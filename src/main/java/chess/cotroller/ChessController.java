package chess.cotroller;

import chess.domain.Chess;
import chess.domain.coordinate.Coordinate;
import chess.domain.piece.Team;
import chess.dto.MoveDto;
import chess.service.ChessService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ChessController {
    private static Map<Integer, Chess> gameMap = new HashMap<>();
    private ChessService chessService = new ChessService();

    public Object move(Request request, Response response) {
        int roomId = Integer.parseInt(request.queryParams("roomId"));
        Coordinate source = Coordinate.of(request.queryParams("source"));
        Coordinate target = Coordinate.of(request.queryParams("target"));
        return chessService.move(new MoveDto(roomId, source, target));
    }

    public Object getMovableWay(Request request, Response response) {
        int roomId = Integer.parseInt(request.queryParams("roomId"));
        Team team = Team.valueOf(request.queryParams("team"));
        Coordinate coordinate = Coordinate.of(request.queryParams("coordinate"));
        return chessService.getMovableWay(roomId, team, coordinate);
    }

    public Object renew(Request request, Response response) {
        int roomId = Integer.parseInt(request.queryParams("roomId"));
        return chessService.renew(roomId);
    }
}
