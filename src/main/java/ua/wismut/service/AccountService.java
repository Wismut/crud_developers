package ua.wismut.service;

import org.springframework.validation.BindingResult;
import ua.wismut.model.Account;

import java.util.List;

public interface AccountService {
    Account save(Account skill, BindingResult bindingResult);

    Account update(Account skill, BindingResult bindingResult);

    void deleteById(Long id);

    List<Account> findAll();

    Account findById(Long id);
}
