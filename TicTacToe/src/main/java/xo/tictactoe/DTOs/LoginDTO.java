package xo.tictactoe.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
    @NotEmpty(message = "Username must not be empty")
    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
