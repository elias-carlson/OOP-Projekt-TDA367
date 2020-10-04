package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.PieceType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class TestSpecialMoves {
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
    public void testCastlingNotPossible() {
        model.handleBoardClick(4,7);
        model.handleBoardClick(6,7);

        assertEquals(boardMap.get(new Point(5,7)).getPieceType(), PieceType.BISHOP);
        assertEquals(boardMap.get(new Point(6,7)).getPieceType(), PieceType.KNIGHT);
    }

    @Test
    public void testCastlingPossible() {
        //Castling setup
        model.handleBoardClick(6,7);
        model.handleBoardClick(7,5);

        model.handleBoardClick(4,1);
        model.handleBoardClick(4,2);

        model.handleBoardClick(4,6);
        model.handleBoardClick(4,5);

        model.handleBoardClick(4,2);
        model.handleBoardClick(4,3);

        model.handleBoardClick(5,7);
        model.handleBoardClick(4,6);

        model.handleBoardClick(4,3);
        model.handleBoardClick(4,4);

        //The actual castling
        model.handleBoardClick(4,7);
        model.handleBoardClick(6,7);

        assertEquals(boardMap.get(new Point(5,7)).getPieceType(), PieceType.ROOK);
        assertEquals(boardMap.get(new Point(6,7)).getPieceType(), PieceType.KING);
    }
}