package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    Map<Integer, Piece> boardConfig;

    Color nextMoveMaker;

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

}
