package xo.tictactoe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xo.tictactoe.entities.Game;
import xo.tictactoe.entities.Users;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "game_moves")
public class GameMove {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "move_id")
    private UUID moveId;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Users player;

    @Column(name = "move_position", nullable = false)
    private String movePosition;

    @Column(name = "move_time", nullable = false)
    private LocalDateTime movetime;

}
