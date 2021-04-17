package chess.exception;

public class NoLogsException extends RoomException {
    public NoLogsException(final String roomId) {
        super(roomId, "이어할 게임이 없습니다. 새로운게임을 눌러주세요!");
    }
}
