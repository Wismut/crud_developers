package ua.wismut.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Account;
import ua.wismut.repository.AccountRepository;
import ua.wismut.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account skill, BindingResult bindingResult) {
        return accountRepository.save(skill);
    }

    @Override
    public Account update(Account skill, Long id, BindingResult bindingResult) {
        return accountRepository.save(skill);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
