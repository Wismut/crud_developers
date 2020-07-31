package ua.wismut.service;

import org.springframework.validation.BindingResult;
import ua.wismut.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User update(User user);

    User save(User user, BindingResult bindingResult);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(Long id);

    void delete(User user);
}
