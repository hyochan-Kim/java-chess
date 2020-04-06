package chess.repository;

import chess.domain.room.Room;
import chess.result.Result;

public interface RoomRepository {
    Result create(Room room);

    Result findById(int roomId);

    Result findByName(String roomName);

    Result update(Room room);

    Result delete(int roomId);
}
