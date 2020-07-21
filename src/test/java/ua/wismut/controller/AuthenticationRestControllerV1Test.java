package ua.wismut.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.wismut.dto.AuthenticationRequestDto;
import ua.wismut.service.AuthenticationRestService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationRestControllerV1Test {
    @InjectMocks
    private AuthenticationRestControllerV1 controllerUnderTest;

    @Mock
    private AuthenticationRestService authenticationRestService;

    @Mock
    private AuthenticationRequestDto authenticationRequestDto;

    @Test
    void login() {
        controllerUnderTest.login(authenticationRequestDto);
        verify(authenticationRestService, times(1)).login(authenticationRequestDto);
        verifyNoMoreInteractions(authenticationRestService);
    }
}
