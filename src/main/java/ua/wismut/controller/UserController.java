package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.wismut.model.User;
import ua.wismut.service.SecurityService;
import ua.wismut.service.UserService;
import ua.wismut.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/users/")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
        return "redirect:/welcome";
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
