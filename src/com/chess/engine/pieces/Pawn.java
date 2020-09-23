package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(final Color pieceColor, final int piecePosition) {

        super(PieceType.PAWN, piecePosition, pieceColor, true);
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
            if(currentCandidate == 8 && !board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {

                if(this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)){
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                }
                else{
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            } //Legal pawn jump moves
            else if(currentCandidate == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isBlack()) ||
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isWhite()))){
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if(!board.getSquare(behindCandidateDestinationCoordinate).isSquareOccupied() &&
                        !board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            //edge case for white pawns on eighth column
            else if(currentCandidate == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                     (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                if (board.getSquare(candidateDestinationCoordinate).isSquareOccupied()) {
                    final Piece pieceOnCandidate = board.getSquare(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        if (this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion (new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
                else if(board.getEnPassantPawn() != null){
                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition +
                            (this.pieceColor.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                            legalMoves.add(new PawnEnPassantAttackMove(board,
                                    this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
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
                        if (this.pieceColor.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion (new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));

                        }
                        else {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
                else if(board.getEnPassantPawn() != null){
                    if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition -
                            (this.pieceColor.getOppositeDirection()))){
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if(this.pieceColor != pieceOnCandidate.getPieceColor()){
                            legalMoves.add(new PawnEnPassantAttackMove(board,
                                    this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public Piece getPromotionPiece(){
        return new Queen(this.pieceColor, this.piecePosition, false); //For now it only promotes to a queen
    }

    //When a piece is moved, return a new piece with an updated position
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    @Override
    public int locationBonus(){
        return this.pieceColor.pawnBonus(this.piecePosition);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
