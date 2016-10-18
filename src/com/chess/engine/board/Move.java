package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public  abstract class Move {
	final Board board; //we need to keep track of Board
	
	final Piece movedPiece; //we need to keep track of Piece
	
	final int destinationCoordinate; //We need to keep track of where that piece moved
	public static final Move NULL_MOVE=new NullMove();
	
	public Move( Board board,
		         Piece movedPiece,
		         int destinationCoordinate)
	{
		this.board=board;
		this.movedPiece=movedPiece;
		this.destinationCoordinate=destinationCoordinate;
	}
	@Override
	public int hashCode(){
		final int prime=31;
		int result=1;
		
		result=result*prime +this.destinationCoordinate;
		result=result*prime +this.movedPiece.hashCode();
		return result;
	}
	@Override
	public boolean equals(final Object other){
		if(this==other){
			return true;
		}
		if(!(other instanceof Move)){
			return false;
		}
		final Move OtherMove=(Move) other;
		return getDestinationCoordinate()==OtherMove.getDestinationCoordinate()&&
				getMovedPiece().equals(OtherMove.getMovedPiece());
		
	}
	
	
	public int getCurrentCoordinate() {
		// TODO Auto-generated method stub
		return this.getMovedPiece().getPiecePosition();
	}
	public int getDestinationCoordinate() {
		// TODO Auto-generated method stub
		return this.destinationCoordinate ;
	}
	public Piece getMovedPiece(){
		return this.movedPiece;
	}
	public boolean isAttack(){
		return false;
	}
	public boolean isCastlingMove(){
		return false;
	}
	public Piece getAttackedPiece(){
		return null;
	}
	
	public Board execute() {
		final Builder builder =new Builder(); //Helping me to materialize the new move
		for(final Piece piece:this.board.currentPlayer().getActivePieces()){
			//I will return back here..I have to work with hash code and equal with piece
			if(!this.movedPiece.equals(piece))
			{
				builder.setPiece(piece);//we are going to set all live Pieces on new Board
			}
		}
		for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
			
				builder.setPiece(piece);//set all live pieces of opponent
		}
		//move the moved piece
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());//set turn to other
		
		return builder.build();
	}
	public static final class MajorMove extends Move{

		public MajorMove(final Board board, 
				 final Piece movedPiece, 
				 final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
			
		}
		
	}
	public static  class AttackMove extends Move{
		final Piece attackPiece;//we need to keep track piece which is being attack
		
		public AttackMove(final Board board,
				          final Piece movedPiece,
				          final int destinationCoordinate,
				          final Piece attackPiece) 
		{
			super(board, movedPiece, destinationCoordinate);
			this.attackPiece=attackPiece; 
			
		}
		@Override
		public int hashCode(){
			return this.attackPiece.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object other){
			if(this==other)
				return true;
			if(!(other instanceof AttackMove)){
				return false;
			}
			
			final AttackMove otherAttackMove=(AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
		}
		//I will come back here
		/*@Override
		public Board execute() {
			
		}*/
		
		@Override
		public boolean isAttack(){
			return true;
		}
		@Override
		public Piece getAttackedPiece(){
			return this.attackPiece;
		}
		
	}
	public static final class PawnMove extends Move{

		public PawnMove(final Board board, 
				 final Piece movedPiece, 
				 final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
			
		}
		
	}
	public static class PawnAttackMove extends AttackMove{

		public PawnAttackMove(final Board board, 
				              final Piece movedPiece, 
				              final int destinationCoordinate,
				              final Piece attackPiece) {
			super(board, movedPiece, destinationCoordinate,attackPiece);
			
		}
		
	}
	public static class PawnEnPassentAttackMove extends PawnAttackMove{

		public PawnEnPassentAttackMove(final Board board, 
				              final Piece movedPiece, 
				              final int destinationCoordinate,
				              final Piece attackPiece) {
			super(board, movedPiece, destinationCoordinate,attackPiece);
			
		}
		
	}
	public static final class PawnJump extends Move{

		public PawnJump(final Board board, 
				 final Piece movedPiece, 
				 final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
			
		}
		@Override
		public Board execute(){
			final Builder builder =new Builder(); //Helping me to materialize the new move
			for(final Piece piece:this.board.currentPlayer().getActivePieces()){
				//I will return back here..I have to work with hash code and equal with piece
				if(!this.movedPiece.equals(piece))
				{
					builder.setPiece(piece);//we are going to set all live Pieces on new Board
				}
			}
			for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
				
					builder.setPiece(piece);//set all live pieces of opponent
			}
			final Pawn movedPawn=(Pawn)this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		
		}
		
	}
	static abstract class CastleMove extends Move //for interchanging Rook and King 'Special Move'
	{
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		public CastleMove(final Board board, 
				 final Piece movedPiece, 
				 final int destinationCoordinate,
				 final Rook castleRook,
				 final int castleRookStart,
				 final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate);
			this.castleRook=castleRook;
			this.castleRookStart=castleRookStart;
			this.castleRookDestination=castleRookDestination;
			
		}
		public Rook getCastleRook(){
			return this.castleRook;
		}
		@Override
		public boolean isCastlingMove(){
			return true;
		}
		@Override
		public Board execute(){
			final Builder builder=new Builder();
			for(final Piece piece:this.board.currentPlayer().getActivePieces()){
				//I will return back here..I have to work with hash code and equal with piece
				if(!this.movedPiece.equals(piece)  && !this.castleRook.equals(piece))
				{
					builder.setPiece(piece);//we are going to set all live Pieces on new Board
				}
			}
			for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
				
				builder.setPiece(piece);//set all live pieces of opponent
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			//I will come back here
			builder.setPiece(new Rook(this.castleRook.getPieceAlliance(),this.castleRookDestination));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
	}
	public static final class KingSideCastleMove extends CastleMove //Interchanging King and Rook from King side
	{

		public KingSideCastleMove(final Board board, 
				                  final Piece movedPiece, 
				                  final int destinationCoordinate,
				                  final Rook castleRook,
				                  final int castleRookStart,
				                  final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate,castleRook,castleRookStart,castleRookDestination);
			
		}
		@Override
		public String toString(){
			return "0-0";//returning toString convention....no need of it..."I will come back here ,if it is needed or not "
		}
		
	}
	public static final class QueenSideCastleMove extends CastleMove //Interchanging King and Rook from queen side
	{

		public QueenSideCastleMove(final Board board, 
				                   final Piece movedPiece, 
				                   final int destinationCoordinate,
				                   final Rook castleRook,
				                   final int castleRookStart,
				                   final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate,castleRook,castleRookStart,castleRookDestination);
			
		}
		@Override
		public String toString(){
			return "0-0-0";//returning toString convention....no need of it..."I will come back here ,if it is needed or not "
		}
		
	}
	public static final class NullMove extends Move{

		public NullMove() {
			super(null, null, -1);
			
		}
	
		public Board execute(){
			throw new RuntimeException("Going Wrong Man:::Don't you know how to play:::");
			/*final Builder builder=new Builder();
			System.out.println("Sorry man ::I think you have gone wrong ::Move Again");
			return builder.build();*/
		}
		
	}
	public static class MoveFactory{
		private MoveFactory(){
			throw new RuntimeException("Not Instancetiated");
		}
		public static Move createMove(final Board board,
				                      final int currentCoordinate,
				                      final int destinationCoordinate)
		{
			for(final Move move:board.getAllLegalMoves()){
				if(move.getCurrentCoordinate()==currentCoordinate &&
					move.getDestinationCoordinate()==destinationCoordinate){
					return move;
				}
			}
			return NULL_MOVE;
			
		}
		
		
	}
	
	
	
	
	

}
