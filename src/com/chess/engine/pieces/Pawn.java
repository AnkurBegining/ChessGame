package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATE={7,8,9,16};
	public Pawn( Alliance pieceAlliance,int piecePosition) {
		super(PieceType.PAWN,  piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMove(Board board) {
		final List<Move> legalMoves=new ArrayList();
		 for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) //Will move through the loop
		 {
			 int candidateDestinationCoordinate=this.piecePosition+
					 (this.pieceAlliance.getDirection()*currentCandidateOffset);
			 if(!BoardUtils.isValidTileCoorinate(candidateDestinationCoordinate)) //If position is not valid it will continue through  next loop
			 {
				 continue;
			 }
			 if(currentCandidateOffset ==8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) //normal single move of pawn and it is non-attacking 
			 {
				// need to do more work(Promotion of Pawn to most generally Queen)
				legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate)); 
			 }
			 //If Pawn is @ 2nd row or & 7th row then it has 2 option .First it can move one position second it can move two position (straightly)
			 else if(currentCandidateOffset==16 && this.isFirstMove() 
					 && (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) 
					 || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()))
			 {
				 final int behindCandidateDestinationCoordinate=this.piecePosition
						 +(this.pieceAlliance.getDirection()*8); //For checKing next straight position is occupied or not 
				 if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() 
						 && !board.getTile(candidateDestinationCoordinate).isTileOccupied())
				 {
					 //more thing to do ..I will return back to here for modifying
					 legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate)); 
					 
				 }
			 }
			/*if I am(Pawn) @ 7 position 
			  i.e. First column then i can't really attack to one diagonal direction */
			 else if(currentCandidateOffset==7
					 &&(!((BoardUtils.EIGTH_COLUMN[this.piecePosition]
					 && this.pieceAlliance.isWhite()) 
					 || (BoardUtils.FIRST_COLUMN[this.piecePosition] 
					 && this.pieceAlliance.isBlack())))) 
			 {
				 if(board.getTile(candidateDestinationCoordinate).isTileOccupied())//It is occupied 
				 { 
					 final Piece pieceOnCandidate=board.getTile(candidateDestinationCoordinate).getPiece(); //Get the piece where our destination is going to be
					 if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) //It is enemy ... Attacking move
					  {
						 //more thing to do ..I will return back to here for modifying
						 legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
						 
					  }
				 }
			 }
			 /*if I am(Pawn) @ 9 position 
			  i.e. last column then i can't really attack to one diagonal direction */
			 else if(currentCandidateOffset==9
					 &&(!((BoardUtils.FIRST_COLUMN[this.piecePosition]
					 && this.pieceAlliance.isWhite()) 
					 || (BoardUtils.EIGTH_COLUMN[this.piecePosition] 
					 && this.pieceAlliance.isBlack()))))
			 {
				 if(board.getTile(candidateDestinationCoordinate).isTileOccupied())//It is occupied 
				 { 
					 final Piece pieceOnCandidate=board.getTile(candidateDestinationCoordinate).getPiece(); //Get the piece where our destination is going to be
					 if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) //It is enemy ... Attacking move
					  {
						 //more thing to do ..I will return back to here for modifying
						 legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
						 
					  }
				 }
			 }
		 
		 }
		
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	public Pawn movePiece(Move move) {
		//create new Pawn @current update location
		return new Pawn(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());//create new Bishop @ current location
	}
	@Override
	public String toString(){
		return PieceType.PAWN.toString();
	}


}
