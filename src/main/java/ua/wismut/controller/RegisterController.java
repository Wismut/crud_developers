package ua.wismut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.wismut.model.User;
import ua.wismut.model.VerificationResult;
import ua.wismut.service.UserService;
import ua.wismut.service.VerificationService;
import ua.wismut.service.impl.AuthService;

import javax.persistence.RollbackException;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    private final UserService userService;
    private final AuthService authService;
    private final VerificationService verificationService;

    @Autowired
    public RegisterController(UserService userService, AuthService authService,
                              VerificationService verificationService) {
        this.userService = userService;
        this.authService = authService;
        this.verificationService = verificationService;
    }

    @PostMapping
    public void register(@RequestBody User user) {
        try {
            user = userService.save(user);
            System.out.println(user);
        } catch (RollbackException e) {
            return;
        }

//        authService.login(request.getSession(), user);

        VerificationResult result = verificationService.startVerification(user.getPhoneNumber(), "sms");
        if (result.isValid()) {
//            response.sendRedirect("/verify");
        } else {
            userService.delete(user);
//            request.setAttribute("message", String.join("\n", result.getErrors()));
//            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
