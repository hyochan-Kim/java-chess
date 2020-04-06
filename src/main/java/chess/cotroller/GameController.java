package chess.cotroller;

import chess.WebUIChessApplication;
import chess.domain.board.BoardGenerator;
import chess.domain.board.Tile;
import chess.domain.coordinate.Coordinate;
import chess.domain.manager.ChessManager;
import chess.domain.piece.Team;
import chess.dto.BoardDto;
import chess.dto.MoveDto;
import chess.view.PieceRender;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private static Map<Integer, ChessManager> gameMap = new HashMap<>();

    public static Object index(Request request, Response response) {

        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "index.html");
    }

    public static Object start(Request request, Response response) {
        ChessManager chessManager = new ChessManager(BoardGenerator.create());
        int gameId = 0;
        chessManager.countUpPlayer();
        gameMap.put(gameId, chessManager);
        return new Gson().toJson(makeBoardDto(gameId, chessManager));
    }

    public static Object way(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        ChessManager chessManager = gameMap.get(gameId);

        Team team = Team.valueOf(request.queryParams("team"));
        System.out.println("########" + team + " #####" + chessManager.getCurrentTeam());
        if (!chessManager.isTurnOf(team) || chessManager.isNotSameTeam(request.queryParams("coordinate"))) {
            throw new IllegalArgumentException("not your turn");
        }

        Coordinate coordinate = Coordinate.of(request.queryParams("coordinate"));
        List<String> movableWay = chessManager.getMovableWay(coordinate);
        return new Gson().toJson(movableWay);
    }

    public static Object load(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        ChessManager chessManager;
        System.out.println("######" + gameId);
        if (!gameMap.containsKey(gameId)) {
            chessManager = new ChessManager(BoardGenerator.create());
            List<MoveDto> moveDtos = new ArrayList<>();
            for (MoveDto moveDto : moveDtos) {
                chessManager.move(moveDto.getSource(), moveDto.getTarget());
            }
            gameMap.put(gameId, chessManager);
        }
        chessManager = gameMap.get(gameId);
        chessManager.countUpPlayer();
        return new Gson().toJson(makeBoardDto(gameId, chessManager));
    }

    public static Object refresh(Request request, Response response) {
        int gameId = Integer.parseInt(request.queryParams("gameId"));
        ChessManager chessManager;
        chessManager = gameMap.get(gameId);
        if (!chessManager.isKingAlive()) {
            return "lose";
        }
        return new Gson().toJson(makeBoardDto(gameId, chessManager));
    }

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
        ChessManager chessManager = gameMap.get(gameId);
        chessManager.countDownPlayer();
        gameMap.put(gameId, chessManager);
        Map<String, Object> model = new HashMap<>();
        return WebUIChessApplication.render(model, "index.html");
    }

    public static Object move(Request request, Response response) {
        int gameId = Integer.parseInt("0");
        ChessManager chessManager = gameMap.get(gameId);
        chessManager.move(request.queryParams("source"), request.queryParams("target"));
        if (!chessManager.isKingAlive()) {
            return "win";
        }
        gameMap.put(gameId, chessManager);
        return new Gson().toJson(makeBoardDto(gameId, chessManager));
    }

    private static BoardDto makeBoardDto(int gameId, ChessManager chessManager) {
        Map<String, String> test = new HashMap<>();
        for (Map.Entry<Coordinate, Tile> entry : chessManager.getChessBoard().entrySet()) {
            test.put(entry.getKey().toString(), PieceRender.findTokenByPiece(entry.getValue().getPiece()));
        }
        return new BoardDto(test,
                gameId, chessManager.getCurrentTeam(),
                chessManager.calculateBlackScore(), chessManager.calculateWhiteScore(), chessManager.getPlayerCount());
    }
}
