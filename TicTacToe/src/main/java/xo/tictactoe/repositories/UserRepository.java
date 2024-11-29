package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Users;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    boolean existsByUsername(String username);
    Users findByUsername(String username);
    Users findByUid(UUID uuid);
    List<Users> findByUsernameContainingIgnoreCase(String username);
}
