package chess.board.piece;

import chess.board.Variation;

public class King extends Piece {

    public King(final Team team) {
        super(team);
    }

    @Override
    public boolean canMove(final Variation variation) {
        return false;
    }

    @Override
    public boolean isSameTeam(final Team team) {
        return false;
    }
}
