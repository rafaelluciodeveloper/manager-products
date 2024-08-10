package br.com.raaydesenvolvimento.managerproducts.repository;

import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRole(Role.VIEW);
        userRepository.save(user);

        User foundUser = userRepository.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }
}
