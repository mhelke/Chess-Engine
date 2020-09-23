package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

/*
    Move strategies for the AI
 */

public interface MoveStrategy {

    Move execute(Board board);

}
