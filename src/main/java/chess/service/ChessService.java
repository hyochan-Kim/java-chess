package chess.service;

import chess.WebUIChessApplication;
import chess.domain.Chess;
import chess.domain.board.Tile;
import chess.domain.coordinate.Coordinate;
import chess.domain.piece.Team;
import chess.dto.BoardDto;
import chess.repository.CachedChessRepository;
import chess.repository.ChessRepository;
import chess.result.Result;
import chess.view.PieceRender;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessService {
    private ChessRepository chessRepository = new CachedChessRepository();

    public static Object surrender(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        if (!gameMap.containsKey(gameId)) {
            throw new IllegalArgumentException("Invalid gameId");
        }
        gameMap.remove(gameId);

        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "result.html");
    }

    public static Object end(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        if (!gameMap.containsKey(gameId)) {
            throw new IllegalArgumentException("Invalid gameId");
        }
        Chess chess = gameMap.get(gameId);
        gameMap.put(gameId, chess);
        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "index.html");
    }

    public static Object move(Request request, Response response) {
        int gameId = Integer.parseInt("0");
        Chess chess = gameMap.get(gameId);
        chess.move(request.queryParams("source"), request.queryParams("target"));
        if (!chess.isKingAlive()) {
            return "win";
        }
        gameMap.put(gameId, chess);
        return new Gson().toJson(makeBoardDto(gameId, chess));
    }

    private static BoardDto makeBoardDto(int gameId, Chess chess) {
        Map<String, String> test = new HashMap<>();
        for (Map.Entry<Coordinate, Tile> entry : chess.getChessBoard().entrySet()) {
            test.put(entry.getKey().toString(), PieceRender.findTokenByPiece(entry.getValue().getPiece()));
        }
        return new BoardDto(test,
                gameId, chess.getCurrentTeam(),
                chess.calculateBlackScore(), chess.calculateWhiteScore(), chess.getPlayerCount());
    }

    public Result getMovableWay(int chessId, Team team, Coordinate coordinate) {

        Result result = chessRepository.findById(chessId);
        if (!result.isSuccess()) {
            throw new IllegalArgumentException("Can not find chess. chess id : " + chessId);
        }
        Chess chess = (Chess) result.getObject();
        if (!chess.isTurnOf(team) || chess.isTurnOf(coordinate)) {
            throw new IllegalArgumentException("not your turn");
        }

        List<String> movableWay = chess.getMovableWay(coordinate);
        return new Result(true, new Gson().toJson(movableWay));
    }

    public Result refresh(int chessId) {
        Result result = chessRepository.findById(chessId);
        if (!result.isSuccess()) {
            throw new IllegalArgumentException("Can not find chess. chess id : " + chessId);
        }
        Chess chess = (Chess) result.getObject();
        if (!chess.isKingAlive()) {
            return new Result(true, "lose");
        }
        return new Result(true, new Gson().toJson(makeBoardDto(chessId, chess)));
    }

}
