package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Role;
import ua.wismut.model.Status;
import ua.wismut.model.User;
import ua.wismut.repository.RoleRepository;
import ua.wismut.repository.UserRepository;
import ua.wismut.service.impl.UserServiceImpl;
import ua.wismut.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl serviceUnderTest;

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init() {
        Mockito.reset(userRepository);
    }

    @Test
    void deleteById() {
        User user = buildUser();
        serviceUnderTest.deleteById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void delete() {
        User user = buildUser();
        serviceUnderTest.delete(user);
        verify(userRepository, times(1)).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void update() {
        User user = buildUser();
        when(userRepository.save(any())).thenReturn(user);
        User updatedUser = serviceUnderTest.update(user, user.getId());
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void save() {
        User user = buildUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(new Role()));
        User savedUser = serviceUnderTest.save(user, bindingResult);
        assertEquals(user, savedUser);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findById() {
        Optional<User> user = Optional.of(buildUser());
        when(userRepository.findById(user.get().getId())).thenReturn(user);
        Optional<User> userById = serviceUnderTest.findById(user.get().getId());
        assertEquals(user, userById);
        verify(userRepository, times(1)).findById(user.get().getId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findByUsername() {
        User user = buildUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Optional<User> foundedUser = serviceUnderTest.findByUsername(user.getUsername());
        assertEquals(foundedUser.get(), user);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findAll() {
        List<User> users = Collections.singletonList(buildUser());
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = serviceUnderTest.findAll();
        assertEquals(users, allUsers);
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    private User buildUser() {
        return new User(
                1L,
                "name",
                "12345",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                Status.ACTIVE,
                "23421235",
                Collections.singleton(new Role()),
                "12345"
        );
    }
}