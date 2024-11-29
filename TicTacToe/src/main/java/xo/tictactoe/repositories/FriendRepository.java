package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Friend;

import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
}
