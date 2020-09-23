package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {

    private final Board lastBoard; // board before the move is made
    private final Board transitionBoard; //board after the move is made
    private final Move move; //move to be made
    private final MoveStatus moveStatus; //is the move legal
    private final boolean attackMove;

    public MoveTransition(Board lastBoard, Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.lastBoard = lastBoard;
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
        if(move instanceof Move.MajorAttackMove){
            this.attackMove = true;
        } else{
            this.attackMove = false;
        }
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getBoard(){
        return this.transitionBoard;
    }

    public Board getLastBoard(){
        return this.transitionBoard;
    }

    public boolean isAttackMove(){
        return this.attackMove;
    }
}
