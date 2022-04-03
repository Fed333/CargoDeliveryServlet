package com.epam.cargo.service.environment;

import com.epam.cargo.dao.repo.UserRepo;
import com.epam.cargo.entity.User;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Designed specially for mocking user's db repositories
 * So far mocks only User itself, doesn't mock mapped db relationship
 * Doesn't regard given User id, assigns current value of id's counter along saving
 * @author Roman Kovalchuk
 * @since 19.02.2022
 * */
public class UserMockEnvironment {

    private Long id = 1L;

    private Map<Long, User> userStorageById = new HashMap<>();

    private Map<String, User> userStorageByLogin = new HashMap<>();

    public void mockFindById(UserRepo repo){
        Mockito.when(repo.findById(any(Long.class)))
                .thenAnswer(findByIdAnswer());
    }

    public void mockFindAll(UserRepo repo){
        Mockito.when(repo.findAll())
                .thenAnswer(findAllAnswer());
    }

    public void mockFindByLogin(UserRepo repo){
        Mockito.when(repo.findByLogin(anyString()))
                .thenAnswer(findByLoginAnswer());
    }

    public void mockSave(UserRepo repo){
        Mockito.when(repo.save(any(User.class)))
                .thenAnswer(saveAnswer());
    }

    public void mockDelete(UserRepo repo){
         Mockito.doAnswer(invocationOnMock -> {
             User user = invocationOnMock.getArgument(0, User.class);
             userStorageById.remove(user.getId());
             userStorageByLogin.remove(user.getLogin());
            return null;
         }).when(repo).delete(any(User.class));
    }

    private Answer<Object> saveAnswer() {
        return invocationOnMock -> {
            User user = invocationOnMock.getArgument(0, User.class);
            requireValidUser(user);
            user.setId(id++);
            userStorageById.put(user.getId(), user);
            userStorageByLogin.put(user.getLogin(), user);
            return user;
        };
    }

    private void requireValidUser(User user) {
        Objects.requireNonNull(user.getLogin());
    }

    private Answer<Object> findByLoginAnswer() {
        return invocationOnMock -> {
            String login = invocationOnMock.getArgument(0, String.class);
            return userStorageByLogin.get(login);
        };
    }

    private Answer<Object> findAllAnswer() {
        return invocationOnMock -> userStorageById.values();
    }

    private Answer<Object> findByIdAnswer() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(userStorageById.get(id));
        };
    }

    /**
     * Fully mocks UserRepo at once
     * */
    public void mockUserRepoBean(UserRepo userRepo) {
        mockSave(userRepo);
        mockFindAll(userRepo);
        mockFindById(userRepo);
        mockFindByLogin(userRepo);
        mockDelete(userRepo);
    }

    public Map<Long, User> getUserStorageById() {
        return userStorageById;
    }

    public Map<String, User> getUserStorageByLogin() {
        return userStorageByLogin;
    }
}
