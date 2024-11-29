package xo.tictactoe.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xo.tictactoe.DTOs.LoginDTO;
import xo.tictactoe.DTOs.UserDTO;
import xo.tictactoe.Services.UserService;
import xo.tictactoe.entities.Friend;
import xo.tictactoe.entities.Users;
import xo.tictactoe.exceptions.BadRequestException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID uid) {
        return ResponseEntity.ok(userService.getUserById(uid));
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody(required = false) UserDTO userDTO) {
        Users newUser = userService.createUsers(userDTO);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/edit/{uid}")
    public ResponseEntity<Users> editUser(@PathVariable UUID uid, @RequestBody UserDTO userDTO) {
        if(uid == null){
            throw new BadRequestException("uid must not be null");
        }
        Users updatedUser = userService.editUsers(uid, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsers(@RequestParam String keyword) {
        List<Users> users = userService.searchUsers(keyword);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/friends")
    public ResponseEntity<Friend> addFriend(@PathVariable UUID userId, @RequestParam UUID friendId) {
        Friend friendship = userService.addFriend(userId, friendId);
        return ResponseEntity.ok(friendship);
    }
}
