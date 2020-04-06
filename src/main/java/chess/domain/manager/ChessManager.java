package chess.domain.manager;

import chess.domain.board.ChessBoard;
import chess.domain.board.Tile;
import chess.domain.coordinate.Coordinate;
import chess.domain.observer.Observable;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;

import java.util.List;
import java.util.Map;

public class ChessManager implements Observable {
    private final ChessBoard chessBoard;
    private Team currentTeam = Team.WHITE;
    private boolean isKingAlive = true;
    private int playerCount = 0;

    public ChessManager(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        chessBoard.subscribe(this);
    }

    public void move(String source, String target) {
        chessBoard.move(source, target);
    }

    public Map<Coordinate, Tile> getChessBoard() {
        return chessBoard.getChessBoard();
    }

    public boolean isTurnOf(Team team) {
        return this.currentTeam.equals(team);
    }

    public void countUpPlayer() {
        playerCount++;
    }

    public void countDownPlayer() {
        playerCount--;
    }

    public List<String> getMovableWay(Coordinate sourceCoordinate) {
        return chessBoard.getMovableWay(sourceCoordinate);
    }

    public double calculateCurrentTeamScore() {
        return this.chessBoard.calculateScore(this.currentTeam);
    }

    public double calculateBlackScore() {
        return this.chessBoard.calculateScore(Team.BLACK);
    }

    public double calculateWhiteScore() {
        return this.chessBoard.calculateScore(Team.WHITE);
    }

    public boolean isKingAlive() {
        return isKingAlive;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public boolean isNotSameTeam(String source) {
        return chessBoard.isNotSameTeam(source, currentTeam);
    }

    @Override
    public void update(final Object object) {
        if (!(object instanceof Piece)) {
            throw new IllegalArgumentException();
        }
        if (object instanceof King) {
            isKingAlive = false;
            return;
        }
        currentTeam = currentTeam.getOppositeTeam();
    }
}
