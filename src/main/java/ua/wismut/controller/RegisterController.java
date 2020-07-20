package ua.wismut.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.wismut.model.User;
import ua.wismut.model.VerificationResult;
import ua.wismut.service.UserService;
import ua.wismut.service.VerificationService;

import javax.persistence.RollbackException;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final VerificationService verificationService;

    @Autowired
    public RegisterController(UserService userService,
                              VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody User user) {
        VerificationResult result = verificationService.startVerification(user.getPhoneNumber(), "call");
        if (result.isValid()) {
            try {
                user = userService.save(user);
                logger.info("In register: user {} successfully saved", user);
                return ResponseEntity.ok(new ObjectMapper()
                        .createObjectNode()
                        .put("message", user.toString())
                        .toString());
            } catch (RollbackException e) {
                logger.info("In register: user {} did not saved", user);
                return ResponseEntity.badRequest().build();
            }
        }
        logger.info("In register: user {} did not saved", user);
        return ResponseEntity.badRequest().build();
    }
}
