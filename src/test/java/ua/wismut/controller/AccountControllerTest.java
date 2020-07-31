package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Account;
import ua.wismut.service.AccountService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @InjectMocks
    private AccountController controllerUnderTest;

    @Mock
    private AccountService accountService;

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(AccountControllerTest.class);
    }

    @Test
    public void findAllAccountsFoundShouldReturnFoundAccountEntries() throws Exception {
        Account firstAccount = buildAccount(1L, "some name");
        Account secondAccount = buildAccount(2L, "nameAccount");

        when(accountService.findAll()).thenReturn(Arrays.asList(firstAccount, secondAccount));

        List<Account> accounts = controllerUnderTest.findAll();
        assertEquals(accounts.size(), 2);
        assertEquals(accounts.get(0).getId().longValue(), 1L);
        assertEquals(accounts.get(1).getId().longValue(), 2L);

        verify(accountService, times(1)).findAll();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void findByIdShouldReturnFoundAccountEntry() throws Exception {
        Account account = buildAccount();

        when(accountService.findById(account.getId())).thenReturn(account);

        Account foundedAccount = controllerUnderTest.findById(account.getId());
        assertEquals(foundedAccount.getId(), account.getId());
        assertEquals(foundedAccount.getAccountData(), account.getAccountData());

        verify(accountService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void saveShouldReturnSavedAccountEntry() throws Exception {
        Account account = buildAccount();

        when(accountService.save(account, bindingResult)).thenReturn(account);

        Account savedAccount = controllerUnderTest.save(account, bindingResult);
        assertEquals(savedAccount.getId(), account.getId());
        assertEquals(savedAccount.getAccountData(), account.getAccountData());

        verify(accountService, times(1)).save(any(), any());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void updateShouldReturnUpdatedAccountEntry() throws Exception {
        Account account = buildAccount();

        when(accountService.update(account, bindingResult)).thenReturn(account);

        Account updatedAccount = controllerUnderTest.update(account, bindingResult);
        assertEquals(updatedAccount.getId(), account.getId());
        assertEquals(updatedAccount.getAccountData(), account.getAccountData());

        verify(accountService, times(1)).update(any(), any());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void deleteById() {
        Long accountId = 5L;

        controllerUnderTest.deleteById(accountId);

        verify(accountService, times(1)).deleteById(5L);
        verifyNoMoreInteractions(accountService);
    }

    private Account buildAccount() {
        Account account = new Account();
        account.setId(7L);
        account.setAccountData("data");
        return account;
    }

    private Account buildAccount(Long id, String data) {
        Account account = new Account();
        account.setId(id);
        account.setAccountData(data);
        return account;
    }
}
