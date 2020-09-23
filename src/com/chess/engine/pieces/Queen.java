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

public class Queen extends Piece{

    //Array of possible moves for the Queen based on the board
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final Color pieceColor, final int piecePosition) {

        super(PieceType.QUEEN, piecePosition, pieceColor, true);
    }

    public Queen(final Color pieceColor, final int piecePosition, final boolean isFirstMove) {

        super(PieceType.QUEEN, piecePosition, pieceColor, isFirstMove);
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
                            legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }//end piece color if statement

                        break; //Square in the path is occupied - break out of loop. Do not consider other moves legal

                    }//end else
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    /* Special Exclusion Cases */

    //Check if queen is on the first column
    private static boolean isOnFirstColumn(final int currentPosition, final int candidateOffset)  {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 ||
                candidateOffset == -9 || candidateOffset == 7);
    }//end first column exclusion method

    //Check if queen is on the Eighth columns
    private static boolean isOnEiththColumn(final int currentPosition, final int candidateOffset)  {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 ||
                candidateOffset == 1 || candidateOffset == 9);
    }

    //When a piece is moved, return a new piece with an updated position
    @Override
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    @Override
    public int locationBonus(){
        return this.pieceColor.queenBonus(this.piecePosition);
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }
}
