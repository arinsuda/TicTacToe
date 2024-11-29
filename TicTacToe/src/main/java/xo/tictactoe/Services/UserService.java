package xo.tictactoe.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xo.tictactoe.DTOs.UserDTO;
import xo.tictactoe.JWT.JwtTokenProvider;
import xo.tictactoe.entities.Friend;
import xo.tictactoe.entities.Users;
import xo.tictactoe.exceptions.BadRequestException;
import xo.tictactoe.exceptions.ItemNotFoundException;
import xo.tictactoe.exceptions.NotCreatedException;
import xo.tictactoe.repositories.FriendRepository;
import xo.tictactoe.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private FriendRepository friendRepository;

    @Transactional
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Users getUserById(UUID uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new ItemNotFoundException("User with UID " + uid + " NOT FOUND"));
    }

    @Transactional
    public Users createUsers(UserDTO userDTO) {
        userValidationService.validateUserDTO(userDTO);

        Users users = new Users();
        users.setUsername(userDTO.getUsername());
        users.setPassword(userDTO.getPassword());
        users.setAge(userDTO.getAge());
        users.setEmail(userDTO.getEmail());
        users.setCreated_at(LocalDateTime.now());

        try {
            return userRepository.save(users);
        } catch (Exception e) {
            throw new NotCreatedException("An error occurred while creating the user");
        }
    }

    @Transactional
    public Users editUsers(UUID uid, UserDTO userDTO) {
        Users users = userRepository.findById(uid)
                .orElseThrow(() -> new ItemNotFoundException("User with UID " + uid + " NOT FOUND"));

        userValidationService.validateUserDTO(userDTO);

        users.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            users.setPassword(userDTO.getPassword());
        }
        users.setAge(userDTO.getAge());
        users.setEmail(userDTO.getEmail());

        try {
            return userRepository.save(users);
        } catch (Exception e) {
            throw new NotCreatedException("An error occurred while editing the user");
        }
    }

    public List<Users> searchUsers(String keyword) {
        return userRepository.findByUsernameContainingIgnoreCase(keyword);
    }

    public Friend addFriend(UUID userId, UUID friendId) {
        if (userId.equals(friendId)) {
            throw new BadRequestException("You cannot add yourself as a friend");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));
        Users friend = userRepository.findById(friendId)
                .orElseThrow(() -> new ItemNotFoundException("Friend not found"));

        Friend friendship = new Friend();
        friendship.setUser(user);
        friendship.setFriend(friend);
        return friendRepository.save(friendship);
    }
}
