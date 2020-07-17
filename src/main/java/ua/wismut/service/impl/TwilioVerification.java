package ua.wismut.service.impl;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.preview.accSecurity.service.Verification;
import com.twilio.rest.preview.accSecurity.service.VerificationCheck;
import ua.wismut.model.VerificationResult;
import ua.wismut.service.VerificationService;

//@Service
public class TwilioVerification implements VerificationService {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID") == null ?
            "AC7746b951ca7493ff165ecb5a371259f6" :
            System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN") == null ?
            "7f1e91fa33c9d9498c624af4ade969e6" :
            System.getenv("TWILIO_AUTH_TOKEN");
    private static final String VERIFICATION_SID = System.getenv("VERIFICATION_SID") == null ?
            "VA0b5b317f4fb55b9ef54b9025a2018097" :
            System.getenv("VERIFICATION_SID");

    public TwilioVerification() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public VerificationResult startVerification(String phone, String channel) {
        try {
            Verification verification = Verification.creator(VERIFICATION_SID, phone, channel).create();
            return new VerificationResult(verification.getSid());
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }

    public VerificationResult checkVerification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(VERIFICATION_SID, code).setTo(phone).create();
            if ("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
}
