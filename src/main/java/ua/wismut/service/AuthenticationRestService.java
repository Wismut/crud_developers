package ua.wismut.service;

import org.springframework.http.ResponseEntity;
import ua.wismut.dto.AuthenticationRequestDto;

import java.util.Map;

public interface AuthenticationRestService {
    ResponseEntity<Map<String, String>> login(AuthenticationRequestDto requestDto);
}
