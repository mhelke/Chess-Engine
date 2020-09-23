package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> playerMoves, final Collection<Move> opponentMoves ){
        this.board = board;
        this.playerKing = establishKing();
        this.isInCheck = !calculateAttacksOnSquare(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(playerMoves, calculateKingCastles(playerMoves, opponentMoves)));
        //If the list is not empty there is an enemy move that can attack the king

    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    public static Collection<Move> calculateAttacksOnSquare(int piecePosition, Collection<Move> moves) {

        //does an enemy move overlap with the square the king is on?
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    //check if it is a valid board (i.e. there is a king)
    private King establishKing() {
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        //No king on board
        throw new RuntimeException("Not a valid board -- Should not reach this statement");
    }

    private King getPlayerKing(){
        return this.playerKing;
    }

    //Check if a move is legal
    private boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    //check states of the game
    public boolean isCheck(){
        return isInCheck;
    }
    public boolean isCheckmate(){
        return this.isInCheck && !hasEscapeMoves(); //In check and has no escape moves
    }

    public boolean isStalemate(){
        return !this.isInCheck && !hasEscapeMoves(); //Not in check, but also no moves
    }

    //Does  the king have an escape square? Is there a move to get out of check?
    private boolean hasEscapeMoves() {
        for(final Move move : this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()) {
                return true; //if a move is possible, return true
            }
        }
        return false; //if the move is not possible return false
    }

    public boolean isCastled(){
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }


    public MoveTransition makeMove(final Move move){
        //if it is an illegal move, don't make a new board -- the move cannot be done
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        //BoardBuilder will build a new board... The Board class is immutable (final) and cannot be changed
        final Board transitionBoard = move.execute(); //Make the move
        final Collection<Move> kingAttacks = Player.calculateAttacksOnSquare(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());

        //Cannot make the move -- still in check. Keep using the current board
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, this.board, move, MoveStatus.STILL_IN_CHECK);
        }

        //Move is legal -- move has been done.
        return new MoveTransition(this.board, transitionBoard, move, MoveStatus.DONE);
    }

    public MoveTransition undoMove (final Move move) {
        return new MoveTransition(this.board, move.undo(), move, MoveStatus.DONE);
    }

    protected boolean hasCastleOpportunities() {
        return !this.isInCheck && !this.playerKing.isCastled() &&
                (this.playerKing.isKingSideCastleCapable() || this.playerKing.isQueenSideCastleCapable());
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Color getColor();
    public abstract Player getOpponent();
    public abstract Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                             final Collection <Move> opponentsLegals);
}
