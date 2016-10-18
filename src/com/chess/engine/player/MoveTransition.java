package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {
	private final Board transition;
	private final Move move;
	private final MoveStatus moveStatus;
	public MoveTransition(Board transition,Move move,MoveStatus moveStatus){
		this.transition=transition;
		this.move=move;
		this.moveStatus=moveStatus;
	}
	public MoveStatus getMovesStatus() {
		// TODO Auto-generated method stub
		return this.moveStatus;
	}
	

}
