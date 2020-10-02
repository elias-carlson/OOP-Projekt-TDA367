package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestAlternatingTurns {
    ChessFacade model;
    Map<Point, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = new ChessFacade();
        boardMap = model.getGame().getBoard().getBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testAlternatingTurns(){
        Player p1 = model.getGame().getPlayerWhite();
        Player p2 = model.getGame().getPlayerBlack();
        model.handleBoardClick(0,6);
        model.handleBoardClick(0, 5);

        Player currPlayer = model.getGame().getCurrentPlayer();
        assertTrue(currPlayer.equals(p2) && !currPlayer.equals(p1));
    }

    @Test
    public void testTakePieceSuccess(){
        Piece piece = model.getGame().getBoard().getBoardMap().get(new Point(3,7));

        model.handleBoardClick(4,6);
        model.handleBoardClick(4,5);

        model.handleBoardClick(3,1);
        model.handleBoardClick(3,2);

        model.handleBoardClick(3,7);
        model.handleBoardClick(6,4);

        model.handleBoardClick(2,0);
        model.handleBoardClick(6,4);

        assertTrue(model.getGame().getDeadPieces().contains(piece));
    }

    @Test
    public void testTakePieceFail(){
        Piece piece = model.getGame().getBoard().getBoardMap().get(new Point(4, 0));

        model.handleBoardClick(3,0);
        model.handleBoardClick(4,0);

        assertFalse(model.getGame().getDeadPieces().contains(piece));
    }
}