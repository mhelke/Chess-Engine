package com.chess.engine.player.ai;

import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceType;
import com.chess.engine.player.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class PawnStructureAnalyzer {

    public static final int ISOLATED_PAWN_PENALTY = -10;
    public static final int DOUBLED_PAWN_PENALTY = -10;


    private PawnStructureAnalyzer(){/* Nothing here to see */}

    private static Collection<Piece> calculatePawns(Player player){
        return player.getActivePieces().stream().filter(piece -> piece.getPieceType() == PieceType.PAWN).collect(Collectors.toList());
    } // this wasn't my code idea. not clue what it does... maybe change it up later? //TODO


    public static int pawnStructureScore(Player player){
        return doubledPawnPenalty(player) + isolatedPawnPenalty(player);
    }

    public static  int isolatedPawnPenalty(Player player){
        return calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePawns(player)));
    }

    public static int doubledPawnPenalty(Player player){
        return calculateDoubledPawnPenalty(createPawnColumnTable(calculatePawns(player)));
    }

    private static int[] createPawnColumnTable(Collection<Piece> playerPawns){
        final int[] table = new int[8];
        for(Piece pawn : playerPawns){
            table[pawn.getPiecePosition() % 8]++;
        }
        return table;
    }

    private static int calculateIsolatedPawnPenalty(int[] pawnsOnColumnTable){
        int numIsoPawns = 0;
        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0){
            numIsoPawns += pawnsOnColumnTable[0]; // column 0 isolated pawn (a file)
        }
        if(pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0){
            numIsoPawns += pawnsOnColumnTable[7]; // column 7 isolated pawns (h files)
        }
        for(int i = 1; i < pawnsOnColumnTable.length -1; i++){
            if(pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0){
                numIsoPawns += pawnsOnColumnTable[i];
            }
        }
        return numIsoPawns * ISOLATED_PAWN_PENALTY;
    }

    private static int calculateDoubledPawnPenalty(int[] pawnsOnColumnTable){
        int numPawnStacks = 0;
        for(int pawnStack : pawnsOnColumnTable){
            if(pawnStack > 1){
                numPawnStacks += pawnStack;
            }
        }
        return numPawnStacks * DOUBLED_PAWN_PENALTY;
    }

}
