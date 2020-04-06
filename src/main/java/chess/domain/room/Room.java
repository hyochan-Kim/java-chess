package chess.domain.room;

import chess.domain.Chess;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private String name;
    private List<Integer> playerIds;
    private Chess chess;

    public Room(final Chess chess, final String name, final int creator) {
        this.chess = chess;
        this.name = name;
        this.playerIds = new ArrayList<>();
        playerIds.add(creator);
    }

    public void join(int userId) {
        if (getPlayerCount() == 2) {
            throw new IllegalArgumentException("Room is full");
        }
        playerIds.add(userId);
    }

    public void exit(int userId) {
        if (!playerIds.contains(userId)) {
            throw new IllegalArgumentException("player not in this room");
        }
        playerIds.remove(userId);
    }

    private int getPlayerCount() {
        return playerIds.size();
    }

    public Chess getChess() {
        return chess;
    }

    public String getName() {
        return name;
    }

    public int getRoomId() {
        return roomId;
    }
}
