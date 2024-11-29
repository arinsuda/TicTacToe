package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.History;
import xo.tictactoe.entities.Users;

import java.util.List;
import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
    List<History> findByUser(Users user);
    List<History> findByUserUid(UUID userId);
}
