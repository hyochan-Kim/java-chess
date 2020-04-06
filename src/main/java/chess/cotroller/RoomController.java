package chess.cotroller;

import chess.dto.RoomDto;
import chess.service.RoomService;
import chess.utils.validator.RoomValidator;
import spark.Request;
import spark.Response;

public class RoomController {
    private RoomService roomService = new RoomService();

    public Object create(Request request, Response response) {
        String roomName = request.queryParams("roomName");
        int userId = Integer.parseInt(request.queryParams("userId"));

        RoomValidator.checkRoomName(roomName);
        RoomValidator.checkUserId(userId);

        RoomDto roomDto = new RoomDto();
        roomDto.setWhiteUserId(userId);
        roomDto.setName(roomName);

        return roomService.create(roomDto);
    }

    public Object join(Request request, Response response) {
        String roomName = request.queryParams("roomName");
        int userId = Integer.parseInt(request.queryParams("userId"));

        RoomValidator.checkRoomName(roomName);
        RoomValidator.checkUserId(userId);

        return roomService.join(roomName, userId);
    }

    public Object exit(Request request, Response response) {
        int roomId = Integer.parseInt(request.queryParams("roomId"));
        int userId = Integer.parseInt(request.queryParams("userId"));

        return roomService.exit(roomId, userId);
    }

    public Object quit(Request request, Response response) {
        int roomId = Integer.parseInt(request.queryParams("roomId"));

        return roomService.quit(roomId);
    }
}
