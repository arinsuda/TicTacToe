package xo.tictactoe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "game_id")
    private UUID gameId;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Users creator;

    @ManyToOne
    @JoinColumn(name = "opponent_id")
    private Users opponent;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @ManyToOne
    @JoinColumn(name = "first_player_id")
    private Users firstPlayer;

    @Column(name = "current_turn")
    private UUID currentTurn;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Users winner;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum GameStatus{
        started,
        waiting,
        finished,
        inprogress
    }

}
