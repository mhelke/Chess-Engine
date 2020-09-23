package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

import java.text.DecimalFormat;

//Evaluation functions to evaluate a position. Each function can look at something different:
// Such as material, pawn structure, king safety, mobility, etc.

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public MiniMax(final int searchDepth){
        this.evaluator = new StandardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis(); // capture the current time

        Move bestMove = null;

        int highestValue = Integer.MIN_VALUE; // low value to start -- number will be replaced by seen numbers
        int lowestValue = Integer.MAX_VALUE; // Same with max value ^
        int currentValue;

        System.out.println(board.currentPlayerString() + " thinking with depth = " + this.searchDepth); // print out the current depth

        int numMoves = board.currentPlayer().getLegalMoves().size(); // number of possible moves
        int moveCounter = 1;

        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition transition = board.currentPlayer().makeMove(move);
            if(transition.getMoveStatus().isDone()){

                // White is the Maximizing player
                // Black is the Minimizing Player

                // Being the recursion - to decide where to start look at the current player
                currentValue = board.currentPlayer().getColor().isWhite() ?
                        min(transition.getBoard(),  this.searchDepth - 1) : // if white is current player, use min to find black's best move
                        max(transition.getBoard(),  this.searchDepth - 1); // if black is current player use max to find white's best move

                System.out.println(toString() + " analyzing move (" + moveCounter + "/" + numMoves + ") " + move + " score: " +
                        df.format(currentValue*0.01f));

                if(board.currentPlayer().getColor().isWhite() && currentValue >= highestValue){
                    highestValue = currentValue; // set new high value for white
                    bestMove = move; // this is the best move currently for white

                }
                else if(board.currentPlayer().getColor().isBlack() && currentValue <= lowestValue){
                    lowestValue = currentValue; // set new high value for white
                    bestMove = move; // this is the best move currently for white
                }
            }
            moveCounter++; // number of moves looked at
        }

        final long executionTime = System.currentTimeMillis() - startTime; // how long it took to calculate the best move

        // Print stats
        System.out.println(board.currentPlayerString() + " SELECTS " + bestMove + "\n" +
                "Total Moves: " + (moveCounter - 1) + " \n" +
                "Time Calculating: " + df.format((double)executionTime/1000) + " seconds\n" +
                "Rate: " + df.format(1000 *((double)(moveCounter-1)/executionTime))  + " moves/second");

        return bestMove;
    }

    private int min(final Board board, final int depth){

        if(depth == 0 || isEndGameScenario(board)){ // if end of game - cut that tree off
            return this.evaluator.evaluate(board, depth);
        }
        int lowestValue = Integer.MAX_VALUE; // very large number we will never reach
        for(final Move move : board.currentPlayer().getLegalMoves()){ // start with highest number possible (no board will be this high)
            final MoveTransition transition = board.currentPlayer().makeMove(move);
            // go through moves, make them, and score them. Return the lowest value of those legal moves
            if(transition.getMoveStatus().isDone()) {
                final int currentValue = max(transition.getBoard(), depth - 1); // call max to find the next level (ply in chess) in the tree. Hence MinMax
                // Recursion is used in the algorithm^^
                if(currentValue <= lowestValue){
                    lowestValue = currentValue; // set the new lowest value if a better one is found
                }
            }
        }
        return lowestValue;
    }

    private int max(final Board board, final int depth){
        if(depth == 0 || isEndGameScenario(board)){
            return this.evaluator.evaluate(board, depth);
        }
        int highestValue = Integer.MIN_VALUE; // very small number we will never reach
        for(final Move move : board.currentPlayer().getLegalMoves()){ // start with low number possible (no board will be this low)
            final MoveTransition transition = board.currentPlayer().makeMove(move);
            // go through moves, make them, and score them. Return the highest value of those legal moves
            if(transition.getMoveStatus().isDone()) {
                final int currentValue = min(transition.getBoard(), depth - 1); // call min to find the next level (ply in chess) in the tree. Hence MinMax
                // Recursion is used in the algorithm^^
                if(currentValue >= highestValue){
                    highestValue = currentValue; // set the new highest value if a better one is found
                }
            }
        }
        return highestValue;
    }

    // is the game over?
    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isCheckmate() ||
               board.currentPlayer().isStalemate();
    }

    @Override
    public String toString(){
        return "MiniMax"; // Name of the engine
    }
}