package ua.wismut.service.impl;

import com.twilio.rest.verify.v2.service.Verification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TwilioVerificationServiceTest {
    @InjectMocks
    private TwilioVerificationService serviceUnderTest;

    @Mock
    private Verification verification;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void startVerification() {
        serviceUnderTest.startVerification(anyString(), anyString());
        verify(verification, times(1)).creator(anyString(), anyString(), anyString());
    }

    @Test
    void checkVerification() {
    }
}