package chess.dto;

import chess.domain.piece.Team;

import java.util.Map;

public class BoardDto {
    private Map<String, String> board;
    private int gameId;
    private Team team;
    private double blackScore;
    private double whiteScore;
    private int playerCount;

    public BoardDto(Map<String, String> board, int gameId, Team team, double blackScore, double whiteScore, int playerCount) {
        this.board = board;
        this.gameId = gameId;
        this.team = team;
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
        this.playerCount = playerCount;
    }

    public Map<String, String> getBoard() {
        return board;
    }

    public int getGameId() {
        return gameId;
    }

    public Team getTeam() {
        return team;
    }

    public double getBlackScore() {
        return blackScore;
    }

    public double getWhiteScore() {
        return whiteScore;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
