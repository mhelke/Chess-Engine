package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    Map<Integer, Piece> boardConfig; // The Board
    Color nextMoveMaker; // Who's move is next?
    Pawn enPassantPawn; // Is there a pawn that can be taken En Passant? i.e. just performed a pawn jump?

    public BoardBuilder(){

        this.boardConfig = new HashMap<>();
    }

    public BoardBuilder setPiece(final Piece piece){
        this.boardConfig.put(piece.getPiecePosition(), piece);
        return this;
    }

    public BoardBuilder setMoveMaker(final Color nextMoveMaker){
        this.nextMoveMaker = nextMoveMaker;
        return this;
    }

    public Board build(){
        return new Board(this);
    }

    // When a pawn performs a jump mark it here so it can be remembered and calculate as an En Passant Attack
    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }
}
