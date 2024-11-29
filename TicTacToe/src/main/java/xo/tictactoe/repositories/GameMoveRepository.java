package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Game;
import xo.tictactoe.entities.GameMove;
import xo.tictactoe.entities.History;
import xo.tictactoe.entities.Users;

import java.util.List;
import java.util.UUID;

public interface GameMoveRepository extends JpaRepository<GameMove, UUID> {
    List<GameMove> findByGameAndMovePosition(Game game, String position);
    List<History> findHistoryByPlayer(Users users);

}
