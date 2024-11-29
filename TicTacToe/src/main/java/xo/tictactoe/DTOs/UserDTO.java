package xo.tictactoe.DTOs;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class UserDTO {
    private UUID uid;
    @Size(max = 30)
    private String username;
    @Size(max = 50)
    private String password;
    private Integer age;
    @Size(max = 100)
    private String email;
}
