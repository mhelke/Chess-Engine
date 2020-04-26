package com.chess.engine.pieces;


import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    // Every piece has a position
    protected final int piecePosition;
    //Every piece has a color
    protected final Color pieceColor;
    //Check if a piece has only moved once
    protected final boolean isFirstMove;


    Piece(final int piecePosition, final Color pieceColor){
        this.pieceColor = pieceColor;
        this.piecePosition = piecePosition;

        //TODO move work here
        this.isFirstMove = false;

    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Color getPieceColor(){
        return this.pieceColor;
    }


    public boolean isFirstMove(){return this.isFirstMove;}

    //each piece will override this method to calculate the legals moves for the particular piece
    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
