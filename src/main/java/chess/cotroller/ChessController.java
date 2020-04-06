package chess.cotroller;

import chess.WebUIChessApplication;
import chess.domain.Chess;
import chess.domain.board.Tile;
import chess.domain.coordinate.Coordinate;
import chess.domain.piece.Team;
import chess.dto.BoardDto;
import chess.result.Result;
import chess.service.ChessService;
import chess.view.PieceRender;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ChessController {
    private static Map<Integer, Chess> gameMap = new HashMap<>();
    private ChessService chessService = new ChessService();

    public Result getMovableWay(Request request, Response response) {
        int chessId = Integer.parseInt(request.queryParams("chessId"));
        Team team = Team.valueOf(request.queryParams("team"));
        Coordinate coordinate = Coordinate.of(request.queryParams("coordinate"));
        return chessService.getMovableWay(chessId, team, coordinate);
    }

    public Object refresh(Request request, Response response) {
        int chessId = Integer.parseInt(request.queryParams("chessId"));

        return chessService.refresh(chessId);
    }

    public Object surrender(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        if (!gameMap.containsKey(gameId)) {
            throw new IllegalArgumentException("Invalid gameId");
        }
        gameMap.remove(gameId);

        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "result.html");
    }

    public Object end(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        if (!gameMap.containsKey(gameId)) {
            throw new IllegalArgumentException("Invalid gameId");
        }
        Chess chess = gameMap.get(gameId);
        gameMap.put(gameId, chess);
        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "index.html");
    }

    public Object move(Request request, Response response) {
        int gameId = Integer.parseInt("0");
        Chess chess = gameMap.get(gameId);
        chess.move(request.queryParams("source"), request.queryParams("target"));
        if (!chess.isKingAlive()) {
            return "win";
        }
        gameMap.put(gameId, chess);
        return new Gson().toJson(makeBoardDto(gameId, chess));
    }

    private BoardDto makeBoardDto(int gameId, Chess chess) {
        Map<String, String> test = new HashMap<>();
        for (Map.Entry<Coordinate, Tile> entry : chess.getChessBoard().entrySet()) {
            test.put(entry.getKey().toString(), PieceRender.findTokenByPiece(entry.getValue().getPiece()));
        }
        return new BoardDto(test,
                gameId, chess.getCurrentTeam(),
                chess.calculateBlackScore(), chess.calculateWhiteScore(), chess.getPlayerCount());
    }
}
