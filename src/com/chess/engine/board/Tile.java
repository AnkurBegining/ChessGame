package com.chess.engine.board;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
public abstract class Tile {
	protected final int tileCooridinate; //tile number // can only be accessed by subclasses // can only be set once
	private static final Map<Integer,EmptyTile> EMPTY_TILES=createALLPossibleEmptyTile();
	private Tile(int tileCooridinate){
		this.tileCooridinate=tileCooridinate;
	}
	
	private static Map<Integer, EmptyTile> createALLPossibleEmptyTile() {
		final Map<Integer,EmptyTile> emptyTileMap=new HashMap<>();
		for(int i=0;i<BoardUtils.NUM_TILES;i++){
			emptyTileMap.put(i, new EmptyTile(i));
		}
		;
		//return Collections.unmodifiableMap(emptyTileMap);
		return ImmutableMap.copyOf(emptyTileMap); //importing google guava library for immutable class 
		//return emptyTileMap;
	}
	public static Tile createTile(final int tileCooridinate,final Piece piece){
		return piece!=null?new OccupiedTile(tileCooridinate,piece):EMPTY_TILES.get(tileCooridinate);
	}

	public abstract boolean isTileOccupied();
	public abstract Piece getPiece();
	public int getTileCoordinate() {
		// TODO Auto-generated method stub
		return this.tileCooridinate;
	}
	public static final class EmptyTile extends Tile{
		EmptyTile(final int coordinate){
			super(coordinate);
		}
		
		@Override
		public String toString()
		{
			return "-"; //For Empty tile i m going to return a "-"
		}
		
		@Override
		public boolean isTileOccupied(){
			return false;
		}
		
		@Override
		public Piece getPiece(){
			return null;
		}
	}
	public static final class OccupiedTile extends Tile{
		private final Piece pieceOnTile; //No where it be changed outside this class
		OccupiedTile(int tileCoordinate ,Piece pieceOnTile){
			super(tileCoordinate);
			this.pieceOnTile=pieceOnTile;
		}
		
		@Override
		public String toString()
		{
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString(); //For Occupied tile i m going to return  piece on tile
		}
		
		@Override
		public boolean isTileOccupied(){
			return true;
		}
		
		@Override
		public Piece getPiece(){
			return this.pieceOnTile;
		}
 }
	
}

