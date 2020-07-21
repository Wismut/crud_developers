package ua.wismut.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.wismut.service.AuthenticationRestService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationRestControllerV1Test {
    @InjectMocks
    private AuthenticationRestControllerV1 controllerUnderTest;

    @Mock
    private AuthenticationRestService authenticationRestService;

    @Test
    void login() {
        controllerUnderTest.login(any());
        verify(authenticationRestService, times(1)).login(any());
        verifyNoMoreInteractions(authenticationRestService);
    }
}
