package com.chess.engine.player.ai;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceType;
import com.chess.engine.player.Player;

public class PieceSetupAnalyzer {

    private static final int PAWN_IMBALANCE_PENALTY = -200;
    private static final int NO_PAWN_PENALTY = -100;
    private static final int KNIGHT_PAIR_PENALTY = -50;
    private static final int KNIGHT_VS_ROOK_PENALTY = -50;
    private static final int  ROOK_PAIR_PENALTY = -100;
    private static final int KNIGHT_VS_PAWNS_BONUS = 25;
    private static final int ROOK_VS_PAWN_BONUS = 25;
    private static final int BISHOP_PAIR_BONUS = 25;
    private static final int BOTH_BISHOP_PAIR_BONUS = 10;
    private static final int OPPOSITE_COLORED_BISHOP_PENALTY = -50;


    private static int materialAdvantage;

    static int currentNumPawns = 0;
    static int opponentNumPawns = 0;
    static int currentNumKnights = 0;
    static int opponentNumKnights = 0;
    static int currentNumRooks = 0;
    static int opponentNumRooks = 0;
    static int currentNumBishops = 0;
    static int opponentNumBishops = 0;
    static boolean currentDarkSquareBishop;
    static boolean currentLightSquareBishop;
    static boolean opponentDarkSquareBishop;
    static boolean opponentLightSquareBishop;



    private PieceSetupAnalyzer() {/*Nothing  to see here*/}

    public static int calculateMaterialAdvantage(Player player) {
        int currentPlayerMaterial = StandardEvaluator.pieceValue(player);
        int opponentMaterial = StandardEvaluator.pieceValue(player.getOpponent());

        return currentPlayerMaterial - opponentMaterial;
    }

    public static boolean isAhead(Player player) {
        if (materialAdvantage >= 0) {
            return true;
        } else { // losing in material
            return false;
        }
    }

    public static void calculateEqualTrade(Player player) {
        //TODO ? or try
    }

    public static void calculatePieces(Player player){
        // current player
        for(final Piece piece : player.getActivePieces()) {
            if(piece.getPieceType() == PieceType.PAWN){
                currentNumPawns++;
            }
            else if(piece.getPieceType() == PieceType.KNIGHT){
                currentNumKnights++;
            }
            else if(piece.getPieceType() == PieceType.ROOK){
                currentNumRooks++;
            }
            else if(piece.getPieceType() == PieceType.BISHOP){
                if(BoardUtils.squareColor(piece.getPiecePosition()) == 0){
                    currentLightSquareBishop = true;
                }
                else  if(BoardUtils.squareColor(piece.getPiecePosition()) == 1){
                    currentDarkSquareBishop = true;
                }
                currentNumBishops++;
            }
        }
        // other player
        for(final Piece piece : player.getOpponent().getActivePieces()) {
            if(piece.getPieceType() == PieceType.PAWN){
                opponentNumPawns++;
            }
            else if(piece.getPieceType() == PieceType.KNIGHT){
                opponentNumKnights++;
            }
            else if(piece.getPieceType() == PieceType.ROOK){
                opponentNumRooks++;
            }
            else if(piece.getPieceType() == PieceType.BISHOP){
                if(BoardUtils.squareColor(piece.getPiecePosition()) == 0){
                    opponentLightSquareBishop = true;
                }
                else  if(BoardUtils.squareColor(piece.getPiecePosition()) == 1){
                    opponentDarkSquareBishop = true;
                }
                opponentNumBishops++;
            }
        }
    }

    // If a player has no pawns it is harder for them to win generally
    public static int noPawnPenalty(){
        if(currentNumPawns == 0 && opponentNumPawns > 0){
            return PAWN_IMBALANCE_PENALTY;
        }
        else  if(currentNumPawns == 0 && opponentNumPawns == 0){
            return NO_PAWN_PENALTY;
        }
        else{
            return 0;
        }
    }

    public static int knightSetup(){
        int eval = 0;
        if(currentNumKnights == 2){
            eval += KNIGHT_PAIR_PENALTY;
        }
        if(currentNumKnights > opponentNumKnights && opponentNumRooks  > 0){
            eval += KNIGHT_VS_ROOK_PENALTY;
        }
        if(currentNumKnights == 2 && currentNumBishops < 2 && opponentNumBishops == 2){
            eval += KNIGHT_PAIR_PENALTY;
        }
        if(opponentNumPawns >= 5){
            eval += currentNumKnights * KNIGHT_VS_PAWNS_BONUS;
        }
        return eval;
    }


    public static int rookSetup(){
        int eval = 0;
        if(currentNumRooks == 2 && opponentNumRooks == 2){
            eval += ROOK_PAIR_PENALTY;
        }
        if(opponentNumPawns <= 5){
            eval += currentNumRooks * ROOK_VS_PAWN_BONUS;
        }
        return eval;
    }

    public static int bishopSetup(){
        int eval = 0;
        if(currentNumBishops == 2 && opponentNumBishops < 2){
            eval += BISHOP_PAIR_BONUS;
        }
        if(currentNumBishops == 2 && opponentNumBishops == 2){
            eval += BOTH_BISHOP_PAIR_BONUS;
        }
        if(currentNumBishops == 1 && opponentNumBishops == 1){
            if((currentLightSquareBishop && opponentDarkSquareBishop) || (currentDarkSquareBishop  && opponentLightSquareBishop)){
                eval += OPPOSITE_COLORED_BISHOP_PENALTY;
            }
        }
        return eval;
    }

    public static int pieceSetupScore(Player player){
        calculateMaterialAdvantage(player);
        calculatePieces(player);
        return noPawnPenalty() +
                knightSetup() +
                rookSetup() +
                bishopSetup();
    }
}

