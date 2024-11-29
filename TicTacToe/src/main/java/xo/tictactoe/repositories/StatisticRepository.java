package xo.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xo.tictactoe.entities.Statistics;
import xo.tictactoe.entities.Users;

import java.util.Optional;
import java.util.UUID;

public interface StatisticRepository extends JpaRepository<Statistics, UUID> {
    Optional<Statistics> findByUser(Users users);

}
