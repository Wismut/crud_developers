package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.wismut.model.User;
import ua.wismut.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/users/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User update(User user) {
        return userService.save(user);
    }

    public User save(User user) {
        return userService.save(user);
    }

    public Optional<User> findById(Long id) {
        return userService.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userService.findByUsername(username);
    }

    public List<User> findAll() {
        return userService.findAll();
    }

    public void deleteById(Long id) {
        userService.deleteById(id);
    }
}
