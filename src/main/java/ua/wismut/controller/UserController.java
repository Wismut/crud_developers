package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.UserNotFoundException;
import ua.wismut.model.User;
import ua.wismut.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{/id}")
    public User update(@RequestBody User user, @PathVariable Long id) {
        return userService.update(user, id);
    }

    @PostMapping
    public User save(@RequestBody User user, BindingResult bindingResult) {
        return userService.save(user, bindingResult);
    }

    @GetMapping("{/id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/findByUsername")
    public User findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("{/id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
