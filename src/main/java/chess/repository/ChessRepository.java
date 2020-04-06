package chess.repository;

import chess.result.Result;

public interface ChessRepository {
    Result create();

    Result findById(int chessId);

    Result update();

    Result delete(int chessId);
}
