package com.tests.chess;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardBuilder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.player.MoveTransition;
import org.junit.Test;

import static com.chess.engine.board.Move.MoveFactory.*;
import static org.junit.Assert.*;

public class TestStalemate {

    @Test
    public void testAnandKramnikStaleMate() {

        final BoardBuilder builder = new BoardBuilder();
        // Black Layout
        builder.setPiece(new Pawn(Color.BLACK, 14));
        builder.setPiece(new Pawn(Color.BLACK, 21));
        builder.setPiece(new King(Color.BLACK, 36, false, false));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 29));
        builder.setPiece(new King(Color.WHITE, 31, false, false));
        builder.setPiece(new Pawn(Color.WHITE, 39));
        // Set the current player
        builder.setMoveMaker(Color.BLACK);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isStalemate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("f5")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isStalemate());
        assertFalse(t1.getBoard().currentPlayer().isCheck());
        assertFalse(t1.getBoard().currentPlayer().isCheckmate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final BoardBuilder builder = new BoardBuilder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 2, false, false));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 10));
        builder.setPiece(new King(Color.WHITE, 26, false, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isStalemate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("c5"),
                        BoardUtils.getCoordinateAtPosition("c6")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isStalemate());
        assertFalse(t1.getBoard().currentPlayer().isCheck());
        assertFalse(t1.getBoard().currentPlayer().isCheckmate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final BoardBuilder builder = new BoardBuilder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 0, false, false));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 16));
        builder.setPiece(new King(Color.WHITE, 17, false, false));
        builder.setPiece(new Bishop(Color.WHITE, 19));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);
        final Board board = builder.build();
        assertFalse(board.currentPlayer().isStalemate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a7")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isStalemate());
        assertFalse(t1.getBoard().currentPlayer().isCheck());
        assertFalse(t1.getBoard().currentPlayer().isCheckmate());
    }

}