package com.example.neolabs.service.impl;

import com.example.neolabs.dto.*;
import com.example.neolabs.dto.request.*;
import com.example.neolabs.dto.request.auth.AuthenticationRequest;
import com.example.neolabs.dto.request.auth.ForgotPasswordCodeRequestDto;
import com.example.neolabs.dto.request.auth.ForgotPasswordRequestDto;
import com.example.neolabs.dto.request.auth.RegistrationRequest;
import com.example.neolabs.dto.request.update.UpdatePasswordRequest;
import com.example.neolabs.dto.request.update.UpdateUserRequest;
import com.example.neolabs.dto.response.AuthResponse2Role;
import com.example.neolabs.dto.response.AuthenticationResponse;
import com.example.neolabs.entity.ResetPassword;
import com.example.neolabs.entity.User;
import com.example.neolabs.enums.EntityEnum;
import com.example.neolabs.enums.ResultCode;
import com.example.neolabs.enums.Role;
import com.example.neolabs.enums.Status;
import com.example.neolabs.exception.BaseException;
import com.example.neolabs.exception.EntityNotFoundException;
import com.example.neolabs.mapper.UserMapper;
import com.example.neolabs.repository.ResetPasswordRepository;
import com.example.neolabs.repository.UserRepository;
import com.example.neolabs.security.jwt.JWTService;
import com.example.neolabs.service.UserService;
import com.example.neolabs.util.DateUtil;
import com.example.neolabs.util.EmailUtil;
import com.example.neolabs.util.ResponseUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    final UserRepository userRepository;
    final JWTService jwtService;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final ResetPasswordRepository resetPasswordRepository;
    final EmailUtil emailUtil;
    final OperationServiceImpl operationService;
    final ImageUploadServiceImpl imageUploadService;

    @Override
    public ResponseDto registration(RegistrationRequest registrationRequest) {
        if (!userRepository.existsByEmail(registrationRequest.getEmail())) {
            userRepository.save(User.builder()
                    .email(registrationRequest.getEmail())
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .phoneNumber(registrationRequest.getPhoneNumber())
                    .lastVisitDate(LocalDateTime.now(DateUtil.getZoneId()))
                    .status(Status.ACTIVE)
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(Role.ROLE_MANAGER)
                    .build());
            return ResponseDto.builder()
                    .result("User has been added successfully.")
                    .resultCode(ResultCode.SUCCESS)
                    .build();
        } else {
            throw new BaseException("User already exists", HttpStatus.CONFLICT);
        }
    }

    //TODO: fix auth status
    @Override
    public AuthResponse2Role auth(AuthenticationRequest authenticationRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
            User user = (User) authenticate.getPrincipal();
            user.setLastVisitDate(LocalDateTime.now(DateUtil.getZoneId()));//s
            userRepository.save(user);//s
            return new AuthResponse2Role(
                    jwtService.generateToken((User) authenticate.getPrincipal()),
                    user.getRole(),
                    user.getFirstName(),
                    user.getLastName()
            );
        } catch (Exception e) {
            throw new BaseException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public AuthenticationResponse refreshToken(User user) {
        try {
            return jwtService.generateToken(user);
        } catch (Exception e) {
            throw new BaseException("not found user", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        User users = userRepository.findByEmail(forgotPasswordRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException("user not found"));
        String genCode = codeGenerate();
        resetPasswordRepository.save(ResetPassword.builder()
                .code(genCode)
                .dateExpiration(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
                .user(users)
                .isActive(false)
                .build());
        emailUtil.send(forgotPasswordRequestDto.getEmail(), "Код для сброса пароля", genCode);
    }

    @Override
    public String codeGenerate() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replaceAll("-", "");

        return randomUUIDString.substring(0, 6);
    }

    @Override
    public void confirmCode(ForgotPasswordCodeRequestDto forgotPasswordCodeRequestDto) {
        ResetPassword resetPassword = resetPasswordRepository
                .findTopByCodeAndIsActiveOrderByDateExpirationDesc(forgotPasswordCodeRequestDto.getCode(), false)
                .orElseThrow(() -> new BaseException("code undefined", HttpStatus.BAD_REQUEST));
        if (resetPassword.getDateExpiration().isAfter(LocalDateTime.now())) {
            User user = resetPassword.getUser();
            user.setPassword(passwordEncoder.encode(forgotPasswordCodeRequestDto.getNewPassword()));
            resetPassword.setIsActive(true);
            resetPasswordRepository.save(resetPassword);
            userRepository.save(user);
        } else throw new BaseException("code date expired", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void confirmCodeFirst(CodeRequest code) {
        resetPasswordRepository
                .findTopByCodeAndIsActiveOrderByDateExpirationDesc(code.getCode(), false)
                .orElseThrow(() -> new BaseException("code undefined", HttpStatus.BAD_REQUEST));
    }

    @Override
    public void updateProfilePage(Long id, UpdateUserRequest updateUserRequest) {
        User user = id != null ? getUserEntityById(id) :
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setEmail(updateUserRequest.getEmail() != null ? updateUserRequest.getEmail() : user.getEmail());
        user.setFirstName(updateUserRequest.getFirstName() != null ? updateUserRequest.getFirstName() : user.getFirstName());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber() != null ? updateUserRequest.getPhoneNumber() : user.getPhoneNumber());
        user.setLastName(updateUserRequest.getLastName() != null ? updateUserRequest.getLastName() : user.getLastName());
        userRepository.save(user);
    }

    @Override
    public void updateProfileImage(MultipartFile multipartFile, User user) {
        user.setUrlImage(imageUploadService.saveImage(multipartFile));
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = getUserEntityById(id);
        user.setStatus(Status.DELETED);
        user.setDeletedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> search(String email, String firstName, String lastName, String firstOrLastName,
                                String phoneNumber) {
        ExampleMatcher exampleMatcher = getSearchExampleMatcher();
        if (firstOrLastName != null) {
            firstName = firstOrLastName;
            lastName = firstOrLastName;
        }
        User probe = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
        return UserMapper.entityListToDtoList(userRepository.findAll(Example.of(probe, exampleMatcher)));
    }

    @Override
    public List<UserDto> filter(Status status, Role role) {
        ExampleMatcher exampleMatcher = getFilterExampleMatcher();
        User probe = User.builder()
                .status(status)
                .role(role)
                .build();
        return UserMapper.entityListToDtoList(userRepository.findAll(Example.of(probe, exampleMatcher)));
    }

    @Override
    public List<UserDto> getAllUsers(PageRequest pageRequest) {
        return UserMapper.entityListToDtoList(userRepository.findAll(pageRequest).stream().toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.entityToDto(getUserEntityById(userId));
    }

    @Override
    public ResponseDto archiveUserById(Long userId, ArchiveRequest archiveRequest, Boolean isBlacklist) {
        User user = getUserEntityById(userId);
        if (user.getStatus() == Status.ARCHIVED && !isBlacklist) {
            throw new BaseException("User is already archived", HttpStatus.CONFLICT);
        }
        if (user.getStatus() == Status.BLACKLIST && isBlacklist) {
            throw new BaseException("User is already in blacklist", HttpStatus.CONFLICT);
        }
        user.setReason(archiveRequest.getReason());
        user.setStatus(isBlacklist ? Status.BLACKLIST : Status.ARCHIVED);
        user.setArchiveDate(LocalDateTime.now(DateUtil.getZoneId()));
        userRepository.save(user);
        return ResponseUtil.buildSuccessResponse("User has been successfully " + (isBlacklist ? "blacklisted." : "archived."));
    }

    @Override
    public ResponseDto unarchiveUserById(Long userId) {
        User user = getUserEntityById(userId);
        if (user.getStatus() == Status.ACTIVE) {
            throw new BaseException("User is already active.", HttpStatus.CONFLICT);
        }
        user.setStatus(Status.ACTIVE);
        user.setReason(null);
        user.setArchiveDate(null);
        userRepository.save(user);
        return ResponseUtil.buildSuccessResponse("User has been successfully unarchived.");
    }

    @Override
    public List<User> getBlacklist() {
        return userRepository.findAllByStatus(Status.BLACKLIST);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BaseException("User not found", HttpStatus.NOT_FOUND));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return loadUserByUsername(email);
    }

    public Long getCurrentUserId() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new BaseException("User not found", HttpStatus.NOT_FOUND)).getId();
    }

    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(EntityEnum.USER, "id", userId));
    }

    public User getCurrentUserEntity() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new BaseException("User not found", HttpStatus.NOT_FOUND));
    }

    private ExampleMatcher getFilterExampleMatcher(){
        return ExampleMatcher.matchingAll()
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id");
    }

    private ExampleMatcher getSearchExampleMatcher(){
        return ExampleMatcher.matchingAny()
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("phoneNumber", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("id");
    }
}
