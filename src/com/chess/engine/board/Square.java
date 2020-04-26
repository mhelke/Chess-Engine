package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Square {

    protected final int coordinate;

    private static final Map<Integer, EmptySquare> EMPTY_SQUARES_CHACHE = createAllPossibleEmptySquares();

    private static Map<Integer, EmptySquare> createAllPossibleEmptySquares() {

        final Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            emptySquareMap.put(i, new EmptySquare(i));
        }

        // Create a map to store the values.
        return ImmutableMap.copyOf(emptySquareMap);
    }

    //creating a new square

    public static Square createSquare(final int coordinate, final Piece piece){
        return piece != null ? new OccupiedSquare(coordinate, piece) : EMPTY_SQUARES_CHACHE.get(coordinate);
    }


    private Square(final int coordinate) {
        this.coordinate = coordinate;
    } //end Squarer constructor

    // Method to tell if the square is occupied or empty
    public abstract boolean isSquareOccupied();

    // Method to retrieve a piece on a square
    public abstract Piece getPiece();

    // If the square is empty
    public static final class EmptySquare extends Square{

        private  EmptySquare(final int coordinate){
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isSquareOccupied(){
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

public static final class OccupiedSquare extends Square{

        private final Piece pieceOnSquare;

        private OccupiedSquare(int coordinate, final Piece  pieceOnSquare){
            super(coordinate);
            this.pieceOnSquare = pieceOnSquare;
        }

    @Override
    public String toString(){
        return getPiece().getPieceColor().isBlack() ? toString().toLowerCase() :
                getPiece().toString();

    }

    @Override
    public boolean isSquareOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return this.pieceOnSquare;
    }
}


}
