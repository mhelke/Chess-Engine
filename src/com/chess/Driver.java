package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.ChessBoard;

public class Driver {

    public static void main(String[] args){

        Board.createStartingBoard();
        ChessBoard.get().show();

    }
}
