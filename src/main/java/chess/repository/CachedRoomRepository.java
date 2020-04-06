package chess.repository;

import chess.domain.room.Room;
import chess.result.Result;

import java.util.HashMap;
import java.util.Map;

public class CachedRoomRepository implements RoomRepository {
    private static RoomRepository roomRepository = new RoomRepositoryImpl();
    private static Map<Integer, Room> cachedRoom = new HashMap<>();

    @Override
    public Result create(Room room) {
        return roomRepository.create(room);
    }

    @Override
    public Result findById(int roomId) {
        if (cachedRoom.containsKey(roomId)) {
            return new Result(true, cachedRoom.get(roomId));
        }
        Result result = roomRepository.findById(roomId);
        if (result.isSuccess()) {
            Room room = (Room) result.getObject();
            cachedRoom.put(roomId, room);
            return new Result(true, room);
        }
        return new Result(false, null);
    }

    @Override
    public Result findByName(final String roomName) {
        Result result = roomRepository.findByName(roomName);
        if (result.isSuccess()) {
            Room room = (Room) result.getObject();
            cachedRoom.put(room.getRoomId(), room);
            return new Result(true, room);
        }
        return new Result(false, null);
    }


    @Override
    public Result update(final Room room) {
        Result result = roomRepository.update(room);
        if (result.isSuccess()) {
            cachedRoom.put(room.getRoomId(), room);
        }
        return result;
    }

    @Override
    public Result delete(final int roomId) {
        Result result = roomRepository.delete(roomId);
        if (result.isSuccess()) {
            cachedRoom.remove(roomId);
        }
        return result;
    }
}
