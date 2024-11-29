package xo.tictactoe.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xo.tictactoe.entities.History;
import xo.tictactoe.entities.Statistics;
import xo.tictactoe.entities.Users;
import xo.tictactoe.exceptions.ItemNotFoundException;
import xo.tictactoe.repositories.HistoryRepository;
import xo.tictactoe.repositories.StatisticRepository;
import xo.tictactoe.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    public Statistics getStatistics(UUID userId) {
        Users user = userRepository.findByUid(userId);
        if (user == null ){
            throw new ItemNotFoundException("User not found!");
        }
        Statistics statistics = statisticsRepository.findByUser(user)
                .orElseGet(() -> {
                    Statistics newStatistics = new Statistics();
                    newStatistics.setUser(user);
                    newStatistics.setWins(0);
                    newStatistics.setLosses(0);
                    newStatistics.setDraws(0);
                    return statisticsRepository.save(newStatistics);
                });

        return statistics;
    }

    public void updateUserStatistics(UUID userId) {
        List<History> historyList = getUserGameHistory(userId);
        Statistics statistics = getStatistics(userId);

        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (History history : historyList) {
            switch (history.getResult()) {
                case WON:
                    wins++;
                    break;
                case LOST:
                    losses++;
                    break;
                case DRAW:
                    draws++;
                    break;
            }
        }
        statistics.setWins(wins);
        statistics.setLosses(losses);
        statistics.setDraws(draws);
        statisticsRepository.save(statistics);
    }

    public List<History> getUserGameHistory(UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        return historyRepository.findByUser(user);
    }
}
