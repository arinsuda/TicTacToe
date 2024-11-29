package xo.tictactoe.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xo.tictactoe.DTOs.UserDTO;
import xo.tictactoe.exceptions.BadRequestException;
import xo.tictactoe.repositories.UserRepository;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateUserDTO(UserDTO userDTO) {

        if (userDTO == null) {
            throw new BadRequestException("User data is null");
        }

        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new BadRequestException("Username is required");
        }

//        if (isUsernameExists(userDTO.getUsername())) {
//            throw new BadRequestException("Username already exists");
//        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new BadRequestException("Password is required");
        }

        if (userDTO.getAge() == null) {
            throw new BadRequestException("Age is required");
        } else if (userDTO.getAge() <= 0) {
            throw new BadRequestException("Age must be greater than 0");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required");
        }
    }

    public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
