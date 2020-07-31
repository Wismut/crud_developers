package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ua.wismut.model.User;
import ua.wismut.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController controllerUnderTest;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(UserControllerTest.class);
    }

    @Test
    public void findAllUsersFoundShouldReturnFoundUserEntries() throws Exception {
        User firstUser = buildUser(1L, "Mike", "Halts");
        User secondUser = buildUser(2L, "Deny", "Richy");

        when(userService.findAll()).thenReturn(Arrays.asList(firstUser, secondUser));

        List<User> users = controllerUnderTest.findAll();
        assertEquals(2, users.size());
        assertEquals(firstUser, users.get(0));
        assertEquals(secondUser, users.get(1));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findByIdShouldReturnFoundUserEntry() throws Exception {
        User user = buildUser();

        when(userService.findById(user.getId())).thenReturn(Optional.of(user));

        User foundedUser = controllerUnderTest.findById(user.getId());
        assertEquals(user, foundedUser);

        verify(userService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void saveShouldReturnSavedUserEntry() throws Exception {
        User user = buildUser();

        when(userService.save(user, bindingResult)).thenReturn(user);

        User savedUser = controllerUnderTest.save(user, bindingResult);
        assertEquals(user, savedUser);

        verify(userService, times(1)).save(any(), any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateShouldReturnUpdatedUserEntry() throws Exception {
        User user = buildUser();

        when(userService.update(user)).thenReturn(user);

        User updatedUser = controllerUnderTest.update(user);
        assertEquals(user, updatedUser);

        verify(userService, times(1)).update(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void deleteById() {
        Long userId = 5L;

        controllerUnderTest.deleteById(userId);

        verify(userService, times(1)).deleteById(5L);
        verifyNoMoreInteractions(userService);
    }

    private User buildUser() {
        User user = new User();
        user.setId(82L);
        user.setUsername("login");
        user.setPassword("secret");
        return user;
    }

    private User buildUser(Long id, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
