package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
/*
    Evaluator to evaluate the board
 */
public interface BoardEvaluator {

    // Chess is a zero sum game. Good for white = good for black remember.
    int evaluate(Board board, int depth);

}
