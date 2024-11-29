package xo.tictactoe.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xo.tictactoe.Services.GameService;
import xo.tictactoe.Services.StatisticService;
import xo.tictactoe.entities.*;
import xo.tictactoe.exceptions.ItemNotFoundException;
import xo.tictactoe.repositories.GameInviteRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestParam UUID creatorId) {
        Game game = gameService.createGame(creatorId);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{gameId}/invite")
    public ResponseEntity<Void> invitePlayer(@PathVariable UUID gameId, @RequestParam UUID friendId) {
        gameService.invitePlayer(gameId, friendId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{gameId}/accept")
    public ResponseEntity<Game> acceptInvitation(@PathVariable UUID gameId, @RequestParam UUID playerId) {
        Game game = gameService.acceptInvitation(gameId, playerId);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{gameId}/play")
    public ResponseEntity<GameMove> playMove(
            @PathVariable UUID gameId,
            @RequestParam UUID playerId,
            @RequestParam int position) {
        GameMove move = gameService.playMove(gameId, playerId, position);
        return ResponseEntity.ok(move);
    }

    @GetMapping("/{gameId}/status")
    public ResponseEntity<Game> getStatus(@PathVariable UUID gameId) {
        Game game = gameService.getStatus(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/invitations")
    public ResponseEntity<List<GameInvite>> getInvitations(@RequestParam UUID userId) {
        List<GameInvite> invitations = gameService.getInviter(userId);
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Game>> getUserGameHistory(@PathVariable UUID userId) {
        List<Game> games = gameService.getPlayerId(userId);
        if (games.isEmpty()) {
            throw new ItemNotFoundException("No game history found for user ID " + userId);
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{userId}/statistics")
    public ResponseEntity<Statistics> getUserStatistics(@PathVariable UUID userId) {
        Statistics statistics = statisticService.getStatistics(userId);
        return ResponseEntity.ok(statistics);
    }

    @PostMapping("/{userId}/update-statistics")
    public ResponseEntity<Void> updateUserStatistics(@PathVariable UUID userId) {
        statisticService.updateUserStatistics(userId);
        return ResponseEntity.ok().build();
    }
}
