package com.chess.engine;

import com.chess.engine.board.Board;

public class Driver {

    public static void main(String[] args){

        Board board = Board.createStartingBoard();

        System.out.println(board);

    }
}
