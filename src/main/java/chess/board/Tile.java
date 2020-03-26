package chess.board;

import chess.board.piece.Piece;
import chess.board.piece.Pieces;

public class Tile {
    private final Coordinate coordinate;
    private Piece piece;

    public Tile(final Coordinate coordinate, final Piece piece) {
        this.coordinate = coordinate;
        this.piece = piece;
    }

    public void replacePiece(Tile sourceTile) {
        this.piece = sourceTile.piece;
        sourceTile.piece = Pieces.BLANK.getPiece();
    }

    public boolean isAlliance(final Tile targetTile) {
        return this.piece.isSameTeam(targetTile.piece);
    }

    public boolean canNotReach(final Tile targetTile) {
        Vector vector = targetTile.coordinate.calculateVector(this.coordinate);
        return !this.piece.canMove(vector);
    }

    public Directions findPath(final Tile targetTile) {
        Vector vector = targetTile.coordinate.calculateVector(this.coordinate);
        return new Directions(this.piece.findPath(vector));
    }

    public boolean isBlank() {
        return piece.isBlank();
    }

    public Piece getPiece() {
        return piece;
    }
}
