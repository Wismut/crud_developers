package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.wismut.model.RoleEnum;
import ua.wismut.model.User;
import ua.wismut.repository.RoleRepository;
import ua.wismut.repository.UserRepository;
import ua.wismut.service.UserService;
import ua.wismut.validator.UserValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Bad request: " + bindingResult.getAllErrors());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName(RoleEnum.ROLE_USER.name()).get()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
