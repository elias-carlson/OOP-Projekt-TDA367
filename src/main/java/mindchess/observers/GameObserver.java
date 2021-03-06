package mindchess.observers;

import mindchess.model.enums.ChessColor;

/**
 * Observer used for notifying the controller when something happens in the game.
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface GameObserver {
    void drawPieces();

    void drawDeadPieces();

    void drawLegalMoves();

    void switchedPlayer();

    void updateTimer();

    void pawnPromotionSetup(ChessColor chessColor);

    void pawnPromotionCleanUp();

    void kingInCheck(int x, int y);
}
