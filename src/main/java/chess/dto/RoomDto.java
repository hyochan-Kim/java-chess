package chess.dto;

public class RoomDto {
    private int roomId;
    private String name;
    private int blackUserId;
    private int whiteUserId;
    private boolean isEnd;

    public RoomDto(int roomId, int blackUserId, int whiteUserId, boolean isEnd, String name) {
        this.roomId = roomId;
        this.blackUserId = blackUserId;
        this.whiteUserId = whiteUserId;
        this.isEnd = isEnd;
        this.name = name;
    }

    public RoomDto() {
        this(-1, -1, -1, false, "default");
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBlackUserId() {
        return blackUserId;
    }

    public void setBlackUserId(int blackUserId) {
        this.blackUserId = blackUserId;
    }

    public int getWhiteUserId() {
        return whiteUserId;
    }

    public void setWhiteUserId(int whiteUserId) {
        this.whiteUserId = whiteUserId;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
