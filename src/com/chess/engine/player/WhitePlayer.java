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

public class WhitePlayer extends Player{


    public WhitePlayer(final Board board, final Collection<Move> whiteLegalMoves, final Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                 final Collection<Move> opponentsLegals) {

        if (!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        //White's King-side castle calculation
        //Requirements to castle: first move and not in check
        if(this.playerKing.isFirstMove() && !this.isCheck()){
            //Requirements to castle: nothing can be in the way of the king and rook (square 61 and 62)
            if(!this.board.getSquare(61).isSquareOccupied() &&
               !this.board.getSquare(62).isSquareOccupied()){
                    final Square rookSquare = this.board.getSquare(63); //Square the rook should be on
                    //Requirements to castle: Must be rooks first move and rook must be there
                    if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()){
                        if(Player.calculateAttacksOnSquare(61, opponentsLegals).isEmpty() &&
                           Player.calculateAttacksOnSquare(62, opponentsLegals).isEmpty() &&
                           rookSquare.getPiece().getPieceType().isRook()) {
                            kingCastles.add(new Move.KingSideCastleMove(this.board,
                                            this.playerKing,
                                            62,
                                            (Rook)rookSquare.getPiece(),
                                            rookSquare.getCoordinate(),
                                            61));
                        }
                    }
            }
            //White's queen-side castle
            if(!this.board.getSquare(59).isSquareOccupied() &&
               !this.board.getSquare(58).isSquareOccupied() &&
               !this.board.getSquare(57).isSquareOccupied()){
                    final Square rookSquare = this.board.getSquare(56);
                    if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove() &&
                       Player.calculateAttacksOnSquare(58, opponentsLegals).isEmpty() &&
                       Player.calculateAttacksOnSquare(59, opponentsLegals).isEmpty() &&
                       rookSquare.getPiece().getPieceType().isRook()){
                        if(Player.calculateAttacksOnSquare(57, opponentsLegals).isEmpty() &&
                           Player.calculateAttacksOnSquare(58, opponentsLegals).isEmpty() &&
                           Player.calculateAttacksOnSquare(59, opponentsLegals).isEmpty() &&
                           rookSquare.getPiece().getPieceType().isRook()){
                            kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                            this.playerKing,
                                            58,
                                            (Rook)rookSquare.getPiece(),
                                            rookSquare.getCoordinate(),
                                            59));
                        }
                    }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }

    @Override
    public String toString(){
        return Color.WHITE.toString();
    }



}
