package com.example.neolabs.controller;

import com.example.neolabs.controller.base.BaseController;
import com.example.neolabs.dto.ResponseDto;
import com.example.neolabs.dto.authentication.AuthenticationRequest;
import com.example.neolabs.dto.registration.RegistrationRequest;
import com.example.neolabs.service.CsvExportService;
import com.example.neolabs.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "User")
public class UserController extends BaseController {
    final UserService userService;

    private final CsvExportService csvExportService;


    @RequestMapping(path = "/download")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"users.csv\"");
        csvExportService.writeUsersToCsv(servletResponse.getWriter());
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseDto> auth(@RequestBody AuthenticationRequest authenticationRequest) {
        return constructSuccessResponse(
                userService.auth(authenticationRequest)
        );
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseDto> registration(@RequestBody RegistrationRequest registrationRequest) {
        return constructSuccessResponse(
                userService.registration(registrationRequest)
        );
    }

}
