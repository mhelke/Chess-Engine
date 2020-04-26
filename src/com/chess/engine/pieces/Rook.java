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

public class Rook extends Piece{

    //Array of possible  moves for the Rook based on the board
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};

    public Rook(final Color pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATES){

            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)){

                if(isOnFirstColumn(candidateDestinationCoordinate, currentCandidate) ||
                        isOnEiththColumn(candidateDestinationCoordinate, currentCandidate)){
                    break;
                }//end column checks


                candidateDestinationCoordinate += currentCandidate;

                //Check if it is a valid square coordinate
                if(BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)){
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

                        break; //Square in the path is occupied - break out of loop. Do not consider other moves legal

                    }//end else
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /* Special Exclusion Cases */

    //Check if Rook is on the first column
    private static boolean isOnFirstColumn(final int currentPosition, final int candidateOffset)  {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
    }//end first column exclusion method

    //Check if Rook is on the Eighth columns
    private static boolean isOnEiththColumn(final int currentPosition, final int candidateOffset)  {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 );
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
