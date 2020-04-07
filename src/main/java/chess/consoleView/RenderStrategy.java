package chess.consoleView;

import chess.domain.piece.Piece;

public interface RenderStrategy {
    String render(Piece piece);
}
