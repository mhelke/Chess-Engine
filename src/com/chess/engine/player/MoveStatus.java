package com.chess.engine.player;

public enum MoveStatus {

    DONE {
        @Override
        public boolean isDone() {
            return true; //The move was done
        }
    },
    ILLEGAL_MOVE{
        @Override
        public boolean isDone() {
            return false; //The move was NOT done -- it is illegal
        }
    },
    STILL_IN_CHECK{
        @Override
        public boolean isDone() {
            return false; //The move was NOT done -- player still in check
        }
    };

    public abstract boolean isDone();
}
