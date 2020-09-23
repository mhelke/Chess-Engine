package com.chess.engine.pieces;


import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {


    protected final int piecePosition; // Every piece has a position
    protected final Color pieceColor; //Every piece has a color
    protected final boolean isFirstMove; //Check if a piece has only moved once
    protected final PieceType pieceType; //Get Piece Types
    private final int cachedHashCode; //Compute the hashcode. It only needs to be done once since everything is Immutable

    Piece(final PieceType pieceType, final int piecePosition, final Color pieceColor, final boolean isFirstMove){
        this.piecePosition = piecePosition;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computerHashCode();
    }

    private int computerHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceColor.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    //check if two pieces are referencing each other
    //need to override it because default equals is reference equality -- this needs object equality.
    //everything has to be equal.
    @Override
    public boolean equals(final Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() &&
                pieceType == otherPiece.getPieceType() &&
                pieceColor == otherPiece.getPieceColor() &&
                isFirstMove == otherPiece.isFirstMove();

    }

    @Override
    public int hashCode(){
       return this.cachedHashCode;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Color getPieceColor(){
        return this.pieceColor;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }


    public boolean isFirstMove(){return this.isFirstMove;}

    // Return the values of each piece
    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }


    // Each piece will override this method to calculate the legals moves for the particular piece
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move); //Take in a piece and return a new piece with the updated position value

    public abstract int locationBonus(); // bonus for where pieces are placed

}
