package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};


    public King(final Color pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();


        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATE) {

            //normal case
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            //edge cases
            if(isOnFirstColumn(this.piecePosition, currentCandidate) ||
               isOnEighthColumn(this.piecePosition, currentCandidate)){
                continue;
            }

            if(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)) {
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
            }


        }

        return null;
    }

    //check if king is on the first column
    private static boolean isOnFirstColumn(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9  || candidateOffset == -1 ||
                candidateOffset == 7);
    }//end first column exceptions method

    //check if king is on the eighth column
    private static boolean isOnEighthColumn(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);
    }//end second column exclusion method

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}
