package chess.model;

/**
 * Class Player represents a player playing chess and contains attributes for that player
 */
public class Player {
    private final ChessTimer chessTimer = new ChessTimer();
    private String name;
    private final ChessColor chessColor;

    public Player(String name, ChessColor chessColor) {
        this.name = name;
        this.chessColor = chessColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentTime(){
        return chessTimer.getTime();
    }

    public void setTime(int seconds){
        chessTimer.setTime(seconds);
    }

    public ChessColor getColor() {
        return chessColor;
    }

    public ChessTimer getTimer() {
        return chessTimer;
    }
    
}
