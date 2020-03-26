package chess.board.piece;

import chess.board.Vector;

public class Queen extends Piece {

    private static final int QUEEN_SCORE = 9;

    public Queen(final Team team) {
        super(team, QUEEN_SCORE);
    }

    @Override
    public boolean canMove(final Vector vector) {
        return vector.isDiagonal() || vector.isStraight();
    }
}
