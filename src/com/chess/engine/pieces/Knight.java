package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece{

    //candidate legal moves
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};


    public Knight(final Color pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES){
           final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            //Is this move a valid square on the board?
            if(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)){

                //check for exclusions to the rule
                if(isOnFirstColumn(this.piecePosition, currentCandidate) ||
                isOnSecondColumn(this.piecePosition, currentCandidate) ||
                isOnSeventhColumn(this.piecePosition, currentCandidate) ||
                isOnEighthColumn(this.piecePosition, currentCandidate)){
                    continue;
                }//end column exclusion check
                final Square candidateDestinationSquare = board.getSquare(candidateDestinationCoordinate);

                //If the square is empty and in bounds it is a legal move
                if(!candidateDestinationSquare.isSquareOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }//end if statement
                else {
                    final Piece pieceAtDestination = candidateDestinationSquare.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();

                    //is the piece on the square an enemy piece?
                    if (this.pieceColor != pieceColor) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }//end piece color if statement
                }//end else
            }//end if statement
        }//end for loop

        return ImmutableList.copyOf(legalMoves);
    }//end legal moves method

    /* Special Exclusion Cases */

    //check if knight is on the first column
    private static boolean isOnFirstColumn(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17  || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }//end first column exceptions method

    //check if knight is on the second column
    private static boolean isOnSecondColumn(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }//end second column exclusion method

    //check if knight is on the seventh column
    private static boolean isOnSeventhColumn(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
    }//end seventh column exclusion method

    //check if knight is on the eighth column
    private static boolean isOnEighthColumn(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }//end eighth column exclusion method

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

}
