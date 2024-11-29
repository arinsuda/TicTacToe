package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Game;
import xo.tictactoe.entities.Users;

import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Game> findAllByCreator(Users userId);
}
