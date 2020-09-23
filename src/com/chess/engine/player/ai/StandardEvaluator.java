package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;



public class StandardEvaluator implements BoardEvaluator {

    // TODO --- tune the algorithm on own later and make some enhancements

    private static final int CHECK_BONUS = 50; // worth half a pawn if in check (due to it being a forcing move)
    private static final int CHECKMATE_BONUS = 10000; // large bonus if in checkmate (this depends on the depth is is found or how soon it happens) -- sooner = better
    private static final int DEPTH_BONUS = 100; // sooner mate is found in the tree, the more it will be worth
    private static final int CASTLE_BONUS = 60; // 0.6 of a pawn - castling makes the king safe, but later in  the game it doesn't matter as much ..
    private static final int ATTACK_MULTIPLIER = 1; // ?? add more?
    private static final int CENTER_CONTROL_BONUS = 5;
    // TODO ^^ maybe add something to look at the move number to know when castling is good other wise you get bad bonuses???

    private static final StandardEvaluator INSTANCE = new StandardEvaluator();


    public static StandardEvaluator get() {
        return INSTANCE;
    }

    @Override
    public int evaluate(final Board board, final int depth) {

        //subtract the score for white and black
        // + for white advantage
        // - for black advantage
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    private int score(final Player player, final int depth) {

        // return the total evaluation score
        return pieceValue(player) +
                mobility(player) +
                check(player) +
                checkmate(player, depth) +
                castled(player) +
                attacks(player) +
                centerControl(player)  +
                pawnStructure(player)  +
                pieceSetup(player);
        //Also have: kingThreats coming soon
        //TODO weigh these values maybe?

    }

    public String evalDetails(Board board, int depth){
        return(
                "White Mobility : " + mobility(board.whitePlayer()) + "\n") +
                //"White kingThreats : " + kingThreats(board.whitePlayer(), depth) + "\n" +
                "White attacks : " + attacks(board.whitePlayer()) + "\n" +
                "White castle : " + castled(board.whitePlayer()) + "\n" +
                "White Material : " + pieceValue(board.whitePlayer()) + "\n" +
                "White Piece Imbalances : " + pieceSetup(board.whitePlayer()) + "\n"  +
                "White pawnStructure : " + pawnStructure(board.whitePlayer()) + "\n" +
                "---------------------\n" +
                "Black Mobility : " + mobility(board.blackPlayer()) + "\n" +
                //"Black kingThreats : " + kingThreats(board.blackPlayer(), depth) + "\n" +
                "Black attacks : " + attacks(board.blackPlayer()) + "\n" +
                "Black castle : " + castled(board.blackPlayer()) + "\n" +
                "Black Material : " + pieceValue(board.blackPlayer()) + "\n" +
                "Black pawnStructure : " + pawnStructure(board.blackPlayer()) + "\n" +
                "Black Piece Imbalances : " + pieceSetup(board.blackPlayer()) + "\n\n" +
                "Final Score = " + (evaluate(board, depth))/100;
    }

    // calculate the material score for both sides
    static int pieceValue(final Player player){
        int pieceValueScore = 0;
        for(final Piece piece : player.getActivePieces()) {
                pieceValueScore += piece.getPieceValue() + piece.locationBonus();
        }
        return pieceValueScore; // total material and  location bonus for each player
    }

    // how many moves does the player have (space)
    // This will be measured in centipawns
    private static int mobility(Player player) {
        return player.getLegalMoves().size();
    }

    // Is there a check?
    private static int check(Player player){
        return player.getOpponent().isCheck() ? CHECK_BONUS : 0;
    }

    // Is there a checkmate?
    private static int checkmate(Player player, int depth){
        return player.getOpponent().isCheckmate() ? CHECKMATE_BONUS * depthBonus(depth): 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1: DEPTH_BONUS * depth; // 1 pawn is added to score the sooner a checkmate occurs in the search tree
    }

    private static int castled(Player player){
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int attacks(Player player){
        int attackScore = 0;
        for(Move move : player.getLegalMoves()){
            if(move.isAttack()) {
                Piece movedPiece = move.getMovedPiece();
                Piece attackedPiece = move.getAttackedPiece();
                if(movedPiece.getPieceValue() <= attackedPiece.getPieceValue()){
                    attackScore++;
                }
            }
        }
        return attackScore * ATTACK_MULTIPLIER;
    }

    private static  int centerControl(Player player) { // *Squares 18 -> 45 are considered the center
        int directCenterControl = 0;
        int centerControl = 0;
        for (int i = 18; i < 46; i++) {
            centerControl += Player.calculateAttacksOnSquare(i, player.getLegalMoves()).size();
            if(i == 27 || i == 28 || i == 35 || i == 36) {
                directCenterControl++; // direct center squares - more important to control will be worth double
            }
        }
        return (centerControl * CENTER_CONTROL_BONUS) + (directCenterControl * CENTER_CONTROL_BONUS);
    }

    private static int pawnStructure(Player player){
        return PawnStructureAnalyzer.pawnStructureScore(player);
    }

    private static int pieceSetup(Player player){
        return PieceSetupAnalyzer.pieceSetupScore(player);
    }
}
