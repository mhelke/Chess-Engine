package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(final Color pieceColor, final int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidate : CANDIDATE_MOVE_COORDINATE) {

            //Multiple by 1 or -1 to get the direction of the pawn movement based on the color
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * currentCandidate);

            //check if move is on the board
            if(!BoardUtils.isValidSquareCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            //Legal regular pawn move
            if(currentCandidate == 8 && board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {

                //TODO more work to do here -- deal with promotions

                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } //Legal pawn jump moves
            else if(currentCandidate == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite())){
                final int behindCandidateDestinatinCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if(!board.getSquare(behindCandidateDestinatinCoordinate).isSquareOccupied() &&
                        !board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

                }
            }
            //edge case for white pawns on eighth column
            else if(currentCandidate == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                     (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))){
                if(board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {
                    final Piece pieceOnCandidate = board.getSquare(candidateDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        //TODO handle the case where you attack into a promotion
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
            //edge case for black pawns on the first column
            else if(currentCandidate == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                     (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))){
                if(board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {
                    final Piece pieceOnCandidate = board.getSquare(candidateDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        //TODO handle the case where you attack into a promotion
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }

        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

}
