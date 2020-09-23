package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player{


    public BlackPlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, blackLegalMoves, whiteLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                 final Collection<Move> opponentsLegals) {

        if (!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        //Black's King-side castle calculation
        //Requirements to castle: first move and not in check
        if(this.playerKing.isFirstMove() && !this.isCheck()){
            //Requirements to castle: nothing can be in the way of the king and rook (square 61 and 62)
            if(!this.board.getSquare(5).isSquareOccupied() &&
                    !this.board.getSquare(6).isSquareOccupied()){
                final Square rookSquare = this.board.getSquare(7); //Square the rook should be on
                //Requirements to castle: Must be rooks first move and rook must be there
                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnSquare(5, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnSquare(6, opponentsLegals).isEmpty() &&
                            rookSquare.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(this.board,
                                        this.playerKing,
                                        6,
                                        (Rook)rookSquare.getPiece(),
                                        rookSquare.getCoordinate(),
                                        5));
                    }
                }
            }
            //Black's queen-side castle
            if(!this.board.getSquare(1).isSquareOccupied() &&
                    !this.board.getSquare(2).isSquareOccupied() &&
                    !this.board.getSquare(3).isSquareOccupied()){
                final Square rookSquare = this.board.getSquare(0);
                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove() &&
                        Player.calculateAttacksOnSquare(2, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnSquare(3, opponentsLegals).isEmpty() &&
                        rookSquare.getPiece().getPieceType().isRook()){
                    if(Player.calculateAttacksOnSquare(1, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnSquare(2, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnSquare(3, opponentsLegals).isEmpty() &&
                            rookSquare.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                        this.playerKing,
                                        2,
                                        (Rook)rookSquare.getPiece(),
                                        rookSquare.getCoordinate(),
                                        3));
                    }
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public String toString(){
        return Color.BLACK.toString();
    }
}
