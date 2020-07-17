package ua.wismut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.wismut.model.User;
import ua.wismut.service.UserService;
import ua.wismut.service.VerificationService;
import ua.wismut.service.impl.AuthService;

import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@RestController
@RequestMapping("/api/v1/register/")
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
    public void register(@RequestBody User user) throws ServletException, IOException {

//        String phone = request.getParameter("full_phone");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String channel = request.getParameter("channel");

//        String hashed = passwordEncoder.encode(password);

//        User user = new User();
//        user.setUsername(username);
//        user.setPhoneNumber(phone);
//        user.setPassword(password);
        try {
            user = userService.save(user);
        } catch (RollbackException e) {

//            request.setAttribute("message", "User creation failed: " + e.getMessage());
//            request.getRequestDispatcher("/register.jsp").forward(request, response);

            return;
        }

//        authService.login(request.getSession(), user);

//        VerificationResult result = verificationService.startVerification(phone, channel);
//        if (result.isValid()) {
//            response.sendRedirect("/verify");
//        } else {
//            userService.delete(user);
//            request.setAttribute("message", String.join("\n", result.getErrors()));
//            request.getRequestDispatcher("/register.jsp").forward(request, response);
//        }
    }

    @GetMapping
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {
//        request.setAttribute("message", FlashMessageHandler.getMessage(request));
//        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
