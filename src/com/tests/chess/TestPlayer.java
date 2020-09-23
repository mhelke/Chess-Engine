package com.tests.chess;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardBuilder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.MoveTransition;
import org.junit.Test;

import static com.chess.engine.board.Move.MoveFactory.*;
import static org.junit.Assert.*;

public class TestPlayer {

/*
        @Test
        public void testSimpleEvaluation() {
            final Board board = Board.createStartingBoard();
            final MoveTransition t1 = board.currentPlayer()
                    .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                            BoardUtils.getCoordinateAtPosition("e4")));
            assertTrue(t1.getMoveStatus().isDone());
            final MoveTransition t2 = t1.getBoard()
                    .currentPlayer()
                    .makeMove(createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                            BoardUtils.getCoordinateAtPosition("e5")));
            assertTrue(t2.getMoveStatus().isDone());
            assertEquals(StandardBoardEvaluator.get().evaluate(t2.getBoard(), 0), 0);
        }
*/

        @Test
        public void testBug() {
            final Board board = Board.createStartingBoard();
            final MoveTransition t1 = board.currentPlayer()
                    .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("c2"),
                            BoardUtils.getCoordinateAtPosition("c3")));
            assertTrue(t1.getMoveStatus().isDone());
            final MoveTransition t2 = t1.getBoard()
                    .currentPlayer()
                    .makeMove(createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                            BoardUtils.getCoordinateAtPosition("a6")));
            assertTrue(t2.getMoveStatus().isDone());
            final MoveTransition t3 = t2.getBoard()
                    .currentPlayer()
                    .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                            BoardUtils.getCoordinateAtPosition("a4")));
            assertTrue(t3.getMoveStatus().isDone());
            final MoveTransition t4 = t3.getBoard()
                    .currentPlayer()
                    .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                            BoardUtils.getCoordinateAtPosition("d6")));
            assertFalse(t4.getMoveStatus().isDone());
        }

        @Test
        public void testDiscoveredCheck() {
            final BoardBuilder builder = new BoardBuilder();
            // Black Layout
            builder.setPiece(new King(Color.BLACK, 4, false, false));
            builder.setPiece(new Rook(Color.BLACK, 24));
            // White Layout
            builder.setPiece(new Bishop(Color.WHITE, 44));
            builder.setPiece(new Rook(Color.WHITE, 52));
            builder.setPiece(new King(Color.WHITE, 58, false, false));
            // Set the current player
            builder.setMoveMaker(Color.WHITE);
            final Board board = builder.build();
            final MoveTransition t1 = board.currentPlayer()
                    .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("e3"),
                            BoardUtils.getCoordinateAtPosition("b6")));
            assertTrue(t1.getMoveStatus().isDone());
            assertTrue(t1.getBoard().currentPlayer().isCheck());
            final MoveTransition t2 = t1.getBoard()
                    .currentPlayer()
                    .makeMove(createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                            BoardUtils.getCoordinateAtPosition("b5")));
            assertFalse(t2.getMoveStatus().isDone());
            final MoveTransition t3 = t1.getBoard()
                    .currentPlayer()
                    .makeMove(createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                            BoardUtils.getCoordinateAtPosition("e5")));
            assertTrue(t3.getMoveStatus().isDone());
        }

/*
        @Test
        public void testUnmakeMove() {
            final Board board = Board.createStartingBoard();
            final Move m1 = MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                    BoardUtils.getCoordinateAtPosition("e4"));
            final MoveTransition t1 = board.currentPlayer()
                    .makeMove(m1);
            assertTrue(t1.getMoveStatus().isDone());
            t1.getBoard().currentPlayer().getOpponent().undoMove(m1);
        }
*/

        @Test
        public void testIllegalMove() {
            final Board board = Board.createStartingBoard();
            final Move m1 = MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                    BoardUtils.getCoordinateAtPosition("e6"));
            final MoveTransition t1 = board.currentPlayer()
                    .makeMove(m1);
            assertFalse(t1.getMoveStatus().isDone());
        }

}