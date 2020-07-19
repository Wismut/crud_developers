package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.wismut.model.Account;
import ua.wismut.repository.AccountRepository;
import ua.wismut.service.impl.AccountServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl serviceUnderTest;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void before() {
        Mockito.reset(accountRepository);
    }

    @Test
    public void save() {
        Account account = buildAccount();
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Account savedAccount = serviceUnderTest.save(account, any());
        assertEquals(account, savedAccount);
        verify(accountRepository, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void findAll() {
        List<Account> accounts = Collections.singletonList(buildAccount());
        when(accountRepository.findAll()).thenReturn(accounts);
        List<Account> allAccounts = serviceUnderTest.findAll();
        assertEquals(accounts, allAccounts);
        verify(accountRepository, times(1)).findAll();
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void update() {
        Account account = buildAccount();
        when(accountRepository.save(any())).thenReturn(account);
        Account updatedAccount = serviceUnderTest.update(account, eq(1L), null);
        assertEquals(account, updatedAccount);
        verify(accountRepository, times(1)).save(account);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void deleteBy() {
        Account account = buildAccount();
        serviceUnderTest.deleteById(account.getId());
        verify(accountRepository, times(1)).deleteById(account.getId());
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void findById() {
        Account account = buildAccount();
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        Account foundAccount = serviceUnderTest.findById(account.getId());
        assertEquals(account, foundAccount);
        verify(accountRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void saveIfAbsent() {
        Account account = buildAccount();
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        Account savedAccount = serviceUnderTest.save(account, any());
        assertEquals(account, savedAccount);
        verify(accountRepository, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    private Account buildAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountData("data");
        return account;
    }
}
