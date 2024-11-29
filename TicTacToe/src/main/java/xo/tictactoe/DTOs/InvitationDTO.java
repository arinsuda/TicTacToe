package xo.tictactoe.DTOs;

import lombok.Data;

import java.util.UUID;

@Data
public class InvitationDTO {
    private UUID fromUserId;
    private UUID toUserId;
    private UUID gameId;
}
