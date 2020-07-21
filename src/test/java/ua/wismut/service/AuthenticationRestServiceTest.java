package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import ua.wismut.dto.AuthenticationRequestDto;
import ua.wismut.security.JwtTokenProvider;
import ua.wismut.service.impl.AuthenticationRestServiceImpl;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationRestServiceTest {
    @InjectMocks
    private AuthenticationRestServiceImpl serviceUnderTest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationRequestDto authenticationRequestDto;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.createToken(anyString(), anySet())).thenReturn("sdxfgvdfgs");
        ResponseEntity<Map<String, String>> responseEntity = serviceUnderTest.login(authenticationRequestDto);
        verify(authenticationManager, times(1)).authenticate(any());
        verify(jwtTokenProvider, times(1)).createToken(null, Collections.emptySet());
        verifyNoMoreInteractions(authenticationManager, jwtTokenProvider);
    }
}
