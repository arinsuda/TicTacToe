package xo.tictactoe.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xo.tictactoe.entities.*;
import xo.tictactoe.exceptions.BadRequestException;
import xo.tictactoe.exceptions.ItemNotFoundException;
import xo.tictactoe.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMoveRepository gameMoveRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameInviteRepository gameInviteRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    public Game createGame(UUID creatorId) {
        Users user = userRepository.findByUid(creatorId);
        if (user == null) {
            throw new ItemNotFoundException("User not found");
        }

        Game game = new Game();
        game.setCreator(user);
        game.setCreatedAt(LocalDateTime.now());
        game.setGameStatus(Game.GameStatus.waiting);
        return gameRepository.save(game);
    }

    public void invitePlayer(UUID gameId, UUID friendId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ItemNotFoundException("Game not found"));

        Users friend = userRepository.findByUid(friendId);
        if (friend == null) {
            throw new ItemNotFoundException("Friend not found");
        }

        GameInvite gameInvite = new GameInvite();
        gameInvite.setGame(game);
        gameInvite.setInviter(friend);
        gameInvite.setStatus(GameInvite.InvitationStatus.PENDING);

        gameInviteRepository.save(gameInvite);
    }

    public Game acceptInvitation(UUID gameId, UUID playerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ItemNotFoundException("Game not found"));

        GameInvite gameInvite = gameInviteRepository.findByGame(game);

        if (!gameInvite.getInviter().getUid().equals(playerId)) {
            throw new BadRequestException("You are not invited to this game");
        }

        Users player = userRepository.findByUid(playerId);

        game.setFirstPlayer(player);
        game.setGameStatus(Game.GameStatus.inprogress);
        return gameRepository.save(game);
    }

    public GameMove playMove(UUID gameId, UUID playerId, int position) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ItemNotFoundException("Game not found"));

        if (!game.getGameStatus().equals(Game.GameStatus.inprogress)) {
            throw new BadRequestException("Game is not in progress");
        }

        if (!game.getCurrentTurn().equals(playerId)) {
            throw new BadRequestException("Not your turn");
        }

        UUID nextPlayerId = (game.getCurrentTurn().equals(game.getCreator().getUid())) ? game.getOpponent().getUid() : game.getCreator().getUid();
        game.setCurrentTurn(nextPlayerId);

        List<GameMove> existingMoves = gameMoveRepository.findByGameAndMovePosition(game, String.valueOf(position));
        if (!existingMoves.isEmpty()) {
            throw new BadRequestException("Position already taken");
        }

        Users player = userRepository.findByUid(playerId);

        GameMove move = new GameMove();
        move.setGame(game);
        move.setPlayer(player);
        move.setMovePosition(String.valueOf(position));
        move.setMovetime(LocalDateTime.now());

        return gameMoveRepository.save(move);
    }

    public void updateUserStatistics(UUID userId) {
        List<History> historyList = getGameHistory(userId);

        Users user = userRepository.findByUid(userId);
        if (user == null) {
            throw new ItemNotFoundException("User not found");
        }

        Statistics statistics = getStatistics(userId).orElse(null);

        if (statistics == null) {
            statistics = new Statistics();
            statistics.setUser(user);
            statistics.setWins(0);
            statistics.setLosses(0);
            statistics.setDraws(0);
        }

        int wins = 0, losses = 0, draws = 0;

        for (History history : historyList) {
            switch (history.getResult()) {
                case WON -> wins++;
                case LOST -> losses++;
                case DRAW -> draws++;
            }
        }

        statistics.setWins(wins);
        statistics.setLosses(losses);
        statistics.setDraws(draws);

        statisticRepository.save(statistics);
    }

    public List<History> getGameHistory(UUID userId) {
        Users users = userRepository.findByUid(userId);
        if (users == null) {
            throw new ItemNotFoundException("User not found");
        }
        return gameMoveRepository.findHistoryByPlayer(users);
    }

    public Optional<Statistics> getStatistics(UUID userId) {
        Users users = userRepository.findByUid(userId);
        return statisticRepository.findByUser(users);
    }



        public Game getStatus(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ItemNotFoundException("Game not found"));
    }

    public List<GameInvite> getInviter(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ItemNotFoundException("User not found with ID: " + userId);
        }
        return gameInviteRepository.findAllByInviterUid(userId);
    }

    public List<Game> getPlayerId(UUID userId) {
        Users users = userRepository.findByUid(userId);
        return gameRepository.findAllByCreator(users);
    }
}
