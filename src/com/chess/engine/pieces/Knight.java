package com.chess.engine.pieces;

import java.util.ArrayList;

import java.util.*;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import static com.chess.engine.board.Move.MajorMove;
import static com.chess.engine.board.Move.AttackMove;

public class Knight extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES ={-17,-15,-6,6,10,15,17}; // Possible position Move By Horse 

	public Knight( final Alliance pieceAlliance,final int piecePosition) //constructor for Knight indicating its current position and Its Alliance 
	{
		super(PieceType.KNIGHT,piecePosition, pieceAlliance);
		
	}

	@Override
	public Collection<Move> calculateLegalMove(final Board board) // checking nd placing all move
	{
		final List<Move> legalMoves =new ArrayList<>();
		int candidateDestinationCoordinate; // Place where candidate is placing his horse
		for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES) //we want move with this offset.
		{
			
			//adding current position to candidate Move
			candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
			 
			if(isValidTileCoorinate(candidateDestinationCoordinate)) //checking n passing only when player move is valid 
			{
				if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)
				   || isSecondColumnExclusion(this.piecePosition,currentCandidateOffset)
				   ||isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset)
				   ||isEigthColumnExclusion(this.piecePosition,currentCandidateOffset)){
					continue;
				}
				final Tile candidateDestinationTile=board.getTile(candidateDestinationCoordinate);
				 
				if(!candidateDestinationTile.isTileOccupied()) //checking on tile not occupied by some other piece
				 {
					 legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate)); //Adding new non -attacking  Move
				 }
				else //tile is occupied by something else
				{
					final Piece pieceAtDestination =candidateDestinationTile.getPiece(); // need to get that piece at that location
					final Alliance pieceAlliance=pieceAtDestination.getPieceAlliance(); // need to get Alliance of that piece
		            if(this.pieceAlliance!=pieceAlliance) //it mean it is Enemy 
		            {
		            	legalMoves.add(new Move.AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination)); //Adding new attacking Move
		            }
				}
		}
	}
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	public Knight movePiece(Move move) {
		//create new Knight @current update location
		return new Knight(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());//create new Bishop @ current location
	}
	@Override
	public String toString(){
		return PieceType.KNIGHT.toString();
	}

	private static boolean isValidTileCoorinate(int candidateDestinationCoordinate) //Checking valid moves of Knight on tile..Is it not going outside of Chess-Tile Board.
	{
	
		return candidateDestinationCoordinate>=0 && candidateDestinationCoordinate<64;
	}  
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-17 || candidateOffset==-10 || candidateOffset==6 || candidateOffset==15) ;
	}
	private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.SECOND_COLUMN[currentPosition] && ( candidateOffset==-10 || candidateOffset==6 ) ;
	}
	private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ( candidateOffset==-6 || candidateOffset==10 ) ;
	}
	private static boolean isEigthColumnExclusion(final int currentPosition,final int candidateOffset){
		return BoardUtils.EIGTH_COLUMN[currentPosition] && ( candidateOffset==-15 || candidateOffset== -6 || candidateOffset==10 || candidateOffset==17 ) ;
	}
	

}
