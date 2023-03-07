package com.epam.mentoring.repository;

import com.epam.mentoring.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles(profiles = "dev")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testEmptyDatabase() {
        // given

        // when
        Iterable<User> allUsers = userRepository.findAll();

        // then
        assertThat(allUsers).isEmpty();
    }

    @Test
    void testSaveUser() {
        // given
        User user = createUser("firstName", "lastName", "email");

        // when
        User persisted = userRepository.save(user);

        // then
        assertThat(persisted)
                .hasFieldOrPropertyWithValue("firstName", "firstName")
                .hasFieldOrPropertyWithValue("lastName", "lastName")
                .hasFieldOrPropertyWithValue("email", "email");
    }

    @Test
    void testSavingMultipleUsers() {
        // given
        User firstUser = createUser("firstName1", "lastName1", "email1");
        User secondUser = createUser("firstName2", "lastName2", "email2");
        userRepository.saveAll(List.of(firstUser, secondUser));

        // when
        Iterable<User> allUsers = userRepository.findAll();

        // then
        assertThat(allUsers).hasSize(2).contains(firstUser, secondUser);
    }

    @Test
    void testFindUserById() {
        // given
        User firstUser = createUser("firstName1", "lastName1", "email1");
        User secondUser = createUser("firstName2", "lastName2", "email2");
        userRepository.saveAll(List.of(firstUser, secondUser));

        // when
        User user = userRepository.findById(secondUser.getId()).get();

        // then
        assertThat(user).isEqualTo(secondUser);
    }

    private static User createUser(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }
}
