package com.chess.engine.pieces;

//Get the letters for each piece
public enum PieceType {

    /*
        The score given are in CENTIPAWNS (1 point = 0.01 of a pawn in material)
        This is because when an engine evaluates a position it typically uses centipawns when looking at
        position, material, safety, structure, etc.
        So, 1 pawn is 100 centipawns.
        This has been proven to be an efficient and effective way to measure advantages in chess.
        The piece value is the standard value of a piece in a game of chess (1,3,3,5,9,infinity)
        (The king is infinite, but a large number will work fine here)

        NOTE: The bishop is generally favored over the knight, but in terms of basic chess, they are both worth 3 points
        ... in terms of a chess engine, this sometimes varies. I may make adjustments on the Bishop or Knight's value later.
     */

    PAWN("P", 100) {
        @Override
        public boolean isKing() {
            return false;
        }
        @Override
        public boolean isRook() {
            return false;
        }
    },
    KNIGHT("N", 300) {
        @Override
        public boolean isKing() {
            return false;
        }
        @Override
        public boolean isRook() {
            return false;
        }
    },
    BISHOP("B", 300) {
        @Override
        public boolean isKing() {
            return false;
        }
        @Override
        public boolean isRook() {
            return false;
        }
    },
    ROOK("R", 500) {
        @Override
        public boolean isKing() {
            return false;
        }
        @Override
        public boolean isRook() {
            return true;
        }
    },
    QUEEN("Q", 900) {
        @Override
        public boolean isKing() {
            return false;
        }
        @Override
        public boolean isRook() {
            return false;
        }
    },
    KING("K", 10000) {
        @Override
        public boolean isKing() {
            return true;
        }
        @Override
        public boolean isRook() {
            return false;
        }
    };

    private String pieceName;
    private int pieceValue;

    PieceType(final String pieceName, final int pieceValue){
        this.pieceName = pieceName;
        this.pieceValue = pieceValue;
    }

    @Override
    public String toString(){
        return this.pieceName;
    }

    public abstract boolean isKing();

    public abstract boolean isRook();

    // piece values
    public int getPieceValue(){
        return this.pieceValue;
    }
}
