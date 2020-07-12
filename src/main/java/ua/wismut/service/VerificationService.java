package ua.wismut.service;

import ua.wismut.model.VerificationResult;

public interface VerificationService {
    VerificationResult startVerification(String phoneNumber, String channel);

    VerificationResult checkVerification(String phoneNumber, String code);
}
