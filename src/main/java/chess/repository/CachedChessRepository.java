package chess.repository;

import chess.domain.Chess;
import chess.result.Result;

import java.util.HashMap;
import java.util.Map;

public class CachedChessRepository implements ChessRepository {

    private static ChessRepository ChessRepository = new ChessRepositoryImpl();
    private static Map<Integer, Chess> cachedChess = new HashMap<>();

    @Override
    public Result create() {
        return null;
    }

    @Override
    public Result findById(final int chessId) {
        return null;
    }

    @Override
    public Result update() {
        return null;
    }

    @Override
    public Result delete(final int chessId) {
        return null;
    }
}
