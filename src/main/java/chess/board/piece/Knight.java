package chess.board.piece;

import chess.board.Vector;

public class Knight extends Piece {

    public Knight(final Team team) {
        super(team);
    }

    @Override
    public boolean canMove(final Vector vector) {
        return vector.sumOfAbsolute() == 3 && vector.subtractOfAbsolute() == 1;
    }
}
