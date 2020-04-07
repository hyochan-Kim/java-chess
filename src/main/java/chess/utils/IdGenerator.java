package chess.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static AtomicInteger roomSeq = new AtomicInteger(0);
    private static AtomicInteger moveSeq = new AtomicInteger(0);

    public static int generateRoomId() {
        return roomSeq.incrementAndGet();
    }

    public static int generateMoveId() {
        return moveSeq.incrementAndGet();
    }
}