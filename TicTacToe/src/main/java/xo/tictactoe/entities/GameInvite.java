package xo.tictactoe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_invites")
public class GameInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "invite_id")
    private UUID inviteId;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private Users inviter;

    @ManyToOne
    @JoinColumn(name = "invitee_id", nullable = false)
    private Users invitee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvitationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum InvitationStatus {
        PENDING, ACCEPTED, REJECTED
    }
}
