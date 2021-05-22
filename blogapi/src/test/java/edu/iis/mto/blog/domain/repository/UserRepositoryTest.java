package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;

    private String firstName = "Jan",  lastName="Doe",  email = "john@domain.com";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    void shouldStoreANewUser() {

        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    void shouldFindUserPassingArgumentsInCaseOfCaseSensitive(){
        repository.save(user);
        List<User> userList = repository
                .findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(firstName, lastName, email);

        Assertions.assertEquals(1,userList.size());
        Assertions.assertEquals(userList.get(0).getFirstName(),firstName);
        Assertions.assertEquals(userList.get(0).getLastName(),lastName);
        Assertions.assertEquals(userList.get(0).getEmail(),email);
    }

    @Test
    void shouldFindUserPassingArgumentsToUpperCase(){
        repository.save(user);
        List<User> userList = repository
                .findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(firstName.toUpperCase(), lastName.toUpperCase(), email.toUpperCase());

        Assertions.assertEquals(1,userList.size());
        Assertions.assertEquals(userList.get(0).getFirstName(),firstName);
        Assertions.assertEquals(userList.get(0).getLastName(),lastName);
        Assertions.assertEquals(userList.get(0).getEmail(),email);
    }

    @Test
    void shouldFindUserUsingOnlyEmail(){
        repository.save(user);
        List<User> userList = repository
                .findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("dummyValue", "dummyValue", email);

        Assertions.assertEquals(1,userList.size());
        Assertions.assertEquals(userList.get(0).getFirstName(),firstName);
        Assertions.assertEquals(userList.get(0).getLastName(),lastName);
        Assertions.assertEquals(userList.get(0).getEmail(),email);
    }

    @Test
    void shouldNotFindUser(){
        repository.save(this.user);
        List<User> userList = repository
                .findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("dummyValue", "dummyValue","dummyValue");

        Assertions.assertEquals(0,userList.size());
    }

}
