package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Game;
import xo.tictactoe.entities.GameInvite;
import xo.tictactoe.entities.Users;

import java.util.List;
import java.util.UUID;

public interface GameInviteRepository extends JpaRepository<GameInvite, UUID> {
    GameInvite findByGame(Game game);
    List<GameInvite> findAllByInviterUid(UUID inviterUid);

}
