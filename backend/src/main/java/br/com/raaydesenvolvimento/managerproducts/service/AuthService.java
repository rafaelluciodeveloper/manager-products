package br.com.raaydesenvolvimento.managerproducts.service;

import br.com.raaydesenvolvimento.managerproducts.dto.RegisterUserRequest;
import br.com.raaydesenvolvimento.managerproducts.model.User;
import br.com.raaydesenvolvimento.managerproducts.model.enums.Role;
import br.com.raaydesenvolvimento.managerproducts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;


    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterUserRequest user) {
        if(Objects.isNull(user.getRole())){
            user.setRole(Role.EDIT);
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        newUser.setEnabled(true);
        return userRepository.save(newUser);
    }
}
