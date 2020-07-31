package ua.wismut.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.wismut.model.Account;
import ua.wismut.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        accountService.deleteById(id);
    }

    @PostMapping
    public Account save(@RequestBody Account account, BindingResult bindingResult) {
        return accountService.save(account, bindingResult);
    }

    @PutMapping
    public Account update(@RequestBody Account account, BindingResult bindingResult) {
        return accountService.update(account, bindingResult);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> findAll() {
        return accountService.findAll();
    }
}
