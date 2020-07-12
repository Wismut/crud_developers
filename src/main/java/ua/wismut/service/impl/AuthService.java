package ua.wismut.service.impl;

import org.springframework.stereotype.Service;
import ua.wismut.model.User;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {
    public void login(HttpSession session, User user) {
        session.setMaxInactiveInterval(1200);
        session.setAttribute("user", user);
    }


    public void logout(HttpSession session) {
        session.invalidate();
    }
}
