package chess.controller.api;

public class WinnerRes {

    private String winner;

    public WinnerRes(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
