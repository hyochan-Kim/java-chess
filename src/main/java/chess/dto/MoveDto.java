package chess.dto;

import chess.domain.coordinate.Coordinate;

public class MoveDto {
    private int moveId;
    private int roomId;
    private Coordinate source;
    private Coordinate target;

    public MoveDto(int roomId, Coordinate source, Coordinate target) {
        this.roomId = roomId;
        this.source = source;
        this.target = target;
    }

    public int getMoveId() {
        return moveId;
    }

    public Coordinate getSource() {
        return source;
    }

    public int getRoomId() {
        return roomId;
    }

    public Coordinate getTarget() {
        return target;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }
}
