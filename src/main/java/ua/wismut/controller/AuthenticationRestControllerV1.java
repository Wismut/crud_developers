package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.wismut.dto.AuthenticationRequestDto;
import ua.wismut.service.AuthenticationRestService;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {
    private final AuthenticationRestService authenticationRestService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationRestService authenticationRestService) {
        this.authenticationRestService = authenticationRestService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto requestDto) {
        return authenticationRestService.login(requestDto);
    }
}
