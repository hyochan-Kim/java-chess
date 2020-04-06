package chess.service;

import chess.domain.Chess;
import chess.domain.board.BoardGenerator;
import chess.domain.room.Room;
import chess.repository.CachedRoomRepository;
import chess.repository.RoomRepository;
import chess.result.Result;

public class RoomService {
    private RoomRepository roomRepository = new CachedRoomRepository();

    public Result create(String roomName, int userId) {
        Chess chess = new Chess(BoardGenerator.create());
        Room room = new Room(chess, roomName, userId);
        return roomRepository.create(room);
    }


    public Result join(String roomName, int userId) {
        Result result = roomRepository.findByName(roomName);
        if (!result.isSuccess()) {
            throw new IllegalArgumentException("Can not find room. Room name : " + roomName);
        }
        Room room = (Room) result.getObject();
        room.join(userId);
        return roomRepository.update(room);
    }

    public Result exit(int roomId, int userId) {
        Result result = roomRepository.findById(roomId);
        if (!result.isSuccess()) {
            throw new IllegalArgumentException("Can not find room.");
        }
        Room room = (Room) result.getObject();
        room.exit(userId);
        return roomRepository.update(room);
    }

    public Result quit(int roomId) {
        Result result = roomRepository.findById(roomId);
        if (!result.isSuccess()) {
            throw new IllegalArgumentException("Can not find room.");
        }
        return roomRepository.delete(roomId);
    }
}
