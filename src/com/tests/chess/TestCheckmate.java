package com.tests.chess;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardBuilder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.*;
import com.chess.engine.player.MoveTransition;
import org.junit.Test;

import static com.chess.engine.board.Move.MoveFactory.*;
import static org.junit.Assert.*;

public class TestCheckmate {

    @Test
    public void testFoolsMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        assertTrue(t4.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("a7"),
                        BoardUtils.getCoordinateAtPosition("a6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("a5"),
                        BoardUtils.getCoordinateAtPosition("a4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testLegalsMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c8"),
                        BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("g7"),
                        BoardUtils.getCoordinateAtPosition("g6")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("g4"),
                        BoardUtils.getCoordinateAtPosition("d1")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("e8"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getBoard(), BoardUtils.getCoordinateAtPosition("c3"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testSevenMoveMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("d6"),
                        BoardUtils.getCoordinateAtPosition("c7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("h1")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("d8")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testGrecoGame() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("d2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("f6"),
                        BoardUtils.getCoordinateAtPosition("g4")));


        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("h2"),
                        BoardUtils.getCoordinateAtPosition("h3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("g4"),
                        BoardUtils.getCoordinateAtPosition("e3")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("f2"),
                        BoardUtils.getCoordinateAtPosition("e3")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("h4"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testOlympicGame() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("c7"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d7"),
                        BoardUtils.getCoordinateAtPosition("d5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("d5"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("c3"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("d7")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("d1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("g8"),
                        BoardUtils.getCoordinateAtPosition("f6")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("e4"),
                        BoardUtils.getCoordinateAtPosition("d6")));

        assertTrue(t11.getMoveStatus().isDone());
        assertTrue(t11.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testAnotherGame() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("g5"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("h1"),
                        BoardUtils.getCoordinateAtPosition("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testSmotheredMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t8.getMoveStatus().isDone());
        assertTrue(t8.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testHippopotamusMate() {

        final Board board = Board.createStartingBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("b1"),
                        BoardUtils.getCoordinateAtPosition("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("h4"),
                        BoardUtils.getCoordinateAtPosition("g5")));


        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("d2"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("c1"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testBlackburneShillingMate() {

        final Board board = Board.createStartingBoard();

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getBoard(), BoardUtils.getCoordinateAtPosition("g1"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getBoard(), BoardUtils.getCoordinateAtPosition("b8"),
                        BoardUtils.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getBoard(), BoardUtils.getCoordinateAtPosition("f1"),
                        BoardUtils.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getBoard(), BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getBoard(), BoardUtils.getCoordinateAtPosition("f3"),
                        BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                        BoardUtils.getCoordinateAtPosition("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getBoard()
                .currentPlayer()
                .makeMove(createMove(t8.getBoard(), BoardUtils.getCoordinateAtPosition("e5"),
                        BoardUtils.getCoordinateAtPosition("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getBoard()
                .currentPlayer()
                .makeMove(createMove(t9.getBoard(), BoardUtils.getCoordinateAtPosition("g5"),
                        BoardUtils.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getBoard()
                .currentPlayer()
                .makeMove(createMove(t10.getBoard(), BoardUtils.getCoordinateAtPosition("h1"),
                        BoardUtils.getCoordinateAtPosition("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getBoard()
                .currentPlayer()
                .makeMove(createMove(t11.getBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                        BoardUtils.getCoordinateAtPosition("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getBoard()
                .currentPlayer()
                .makeMove(createMove(t12.getBoard(), BoardUtils.getCoordinateAtPosition("c4"),
                        BoardUtils.getCoordinateAtPosition("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getBoard()
                .currentPlayer()
                .makeMove(createMove(t13.getBoard(), BoardUtils.getCoordinateAtPosition("d4"),
                        BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testAnastasiaMate() {

        final BoardBuilder builder = new BoardBuilder();

        // Black Layout
        builder.setPiece(new Rook(Color.BLACK, 0));
        builder.setPiece(new Rook(Color.BLACK, 5));
        builder.setPiece(new Pawn(Color.BLACK, 8));
        builder.setPiece(new Pawn(Color.BLACK, 9));
        builder.setPiece(new Pawn(Color.BLACK, 10));
        builder.setPiece(new Pawn(Color.BLACK, 13));
        builder.setPiece(new Pawn(Color.BLACK, 14));
        builder.setPiece(new King(Color.BLACK, 15, false, false));
        // White Layout
        builder.setPiece(new Knight(Color.WHITE, 12));
        builder.setPiece(new Rook(Color.WHITE, 27));
        builder.setPiece(new Pawn(Color.WHITE, 41));
        builder.setPiece(new Pawn(Color.WHITE, 48));
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 55));
        builder.setPiece(new King(Color.WHITE, 62, false, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(createMove(board, BoardUtils.getCoordinateAtPosition("d5"),
                        BoardUtils.getCoordinateAtPosition("h5")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isCheckmate());
    }

    @Test
    public void testTwoBishopMate() {

        final BoardBuilder builder = new BoardBuilder();

        builder.setPiece(new King(Color.BLACK, 7, false, false));
        builder.setPiece(new Pawn(Color.BLACK, 8));
        builder.setPiece(new Pawn(Color.BLACK, 10));
        builder.setPiece(new Pawn(Color.BLACK, 15));
        builder.setPiece(new Pawn(Color.BLACK, 17));
        // White Layout
        builder.setPiece(new Bishop(Color.WHITE, 40));
        builder.setPiece(new Bishop(Color.WHITE, 48));
        builder.setPiece(new King(Color.WHITE, 53, false, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a3"),
                        BoardUtils.getCoordinateAtPosition("b2")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isCheckmate());
    }

    @Test
    public void testQueenRookMate() {

        final BoardBuilder builder = new BoardBuilder();

        // Black Layout
        builder.setPiece(new King(Color.BLACK, 5, false, false));
        // White Layout
        builder.setPiece(new Rook(Color.WHITE, 9));
        builder.setPiece(new Queen(Color.WHITE, 16));
        builder.setPiece(new King(Color.WHITE, 59, false, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("a6"),
                        BoardUtils.getCoordinateAtPosition("a8")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testQueenKnightMate() {

        final BoardBuilder builder = new BoardBuilder();

        // Black Layout
        builder.setPiece(new King(Color.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Queen(Color.WHITE, 15));
        builder.setPiece(new Knight(Color.WHITE, 29));
        builder.setPiece(new Pawn(Color.WHITE, 55));
        builder.setPiece(new King(Color.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Color.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("h7"),
                        BoardUtils.getCoordinateAtPosition("e7")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isCheckmate());

    }

    @Test
    public void testBackRankMate() {

        final BoardBuilder builder = new BoardBuilder();
        // Black Layout
        builder.setPiece(new King(Color.BLACK, 4, false, false));
        builder.setPiece(new Rook(Color.BLACK, 18));
        // White Layout
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 55));
        builder.setPiece(new King(Color.WHITE, 62, false, false));
        // Set the current player
        builder.setMoveMaker(Color.BLACK);

        final Board board = builder.build();

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("c6"),
                        BoardUtils.getCoordinateAtPosition("c1")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getBoard().currentPlayer().isCheckmate());

    }



}