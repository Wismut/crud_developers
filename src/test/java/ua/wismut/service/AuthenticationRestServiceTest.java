package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import ua.wismut.security.JwtTokenProvider;
import ua.wismut.service.impl.AuthenticationRestServiceImpl;

import javax.naming.AuthenticationException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationRestServiceTest {
    @InjectMocks
    private AuthenticationRestServiceImpl serviceUnderTest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login() {
        when(authenticationManager.authenticate(any())).thenThrow(AuthenticationException.class);
//        assertThrows(AuthenticationException.class, () -> serviceUnderTest.login(any()));
        serviceUnderTest.login(any());
    }
}
