package com.chess.engine.pieces;
import java.util.Collection;

import java.util.List;

import  com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
	protected final PieceType pieceType;
	protected final int piecePosition; //position of piece
	protected final Alliance pieceAlliance; //Piece can have several Attribute,like white and Black color
    protected final boolean isFirstMove;
    private final int cachedHashCode;
	public Piece(final PieceType  pieceType,final int piecePosition,final Alliance pieceAlliance) //Giving initial attribute to Piece
	{
		this.pieceType=pieceType;
		this.pieceAlliance=pieceAlliance;
		this.piecePosition=piecePosition;
		//I have to do more work here
		this.isFirstMove=false;
		this.cachedHashCode=computeHashCode();
	}
	private int computeHashCode()
	{
		int result=pieceType.hashCode();
		result=31*result+pieceAlliance.hashCode();
		result=31*result+piecePosition;
		result=31*result+ (isFirstMove ?1:0);
		return result;
		
	}
	public boolean equals(final Object other)
	{
		if(this==other)
			return true;
		if(!(other instanceof Piece))
			return false;
		final Piece otherPiece=(Piece)other;
		return piecePosition==otherPiece.getPiecePosition() && pieceType==otherPiece.getPieceType() &&
				pieceAlliance==otherPiece.getPieceAlliance() && isFirstMove==otherPiece.isFirstMove();
		
	}
	public int hashCode()
	{
		return this.cachedHashCode;
	}
	public int getPiecePosition() {
		// TODO Auto-generated method stub
		return this.piecePosition;
	}
	public Alliance getPieceAlliance() //getting Alliance of that piece
	{
		return this.pieceAlliance;
	}
	public boolean isFirstMove(){
		return isFirstMove;
	}
	public PieceType getPieceType(){
		 return this.pieceType;
	}
	
	// method going to be responsible for Legal Move
	public abstract Collection<Move> calculateLegalMove(final Board board);
	//its going to take in a move and apply existing piece to ...and update piece position
	public abstract Piece movePiece(Move move);
		
	public enum PieceType
	{
		PAWN("P") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return true;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRook() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		private String pieceName;
		
		PieceType( String pieceName)
		{
			this.pieceName=pieceName;
		}
		@Override
		public String toString()
		{
			return this.pieceName;
		}
		public abstract boolean isKing();
		public abstract boolean isRook();
	}

	
	
}
