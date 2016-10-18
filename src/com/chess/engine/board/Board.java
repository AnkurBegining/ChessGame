package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class Board {
	private final List<Tile> gameBoard; //creating gameBoard
	/*we need to keep track of white piece 
	 * and black piece 
	 */
	private Board chessBoard;
	  void updateGameBoard(final Board board) {
	        this.chessBoard = board;
	    }
	private final Collection<Piece>whitePieces;
	private final Collection<Piece>blackPieces;
	
	private final WhitePlayer whitePlayer; //keeping track of white Player
	private final BlackPlayer blackPlayer; //Keeping track of black Player
	private final Player currentPlayer;
	
	public Board( Builder builder) //constructor for the Board
	{
		this.gameBoard=createGameBoard(builder);//creating Game Board with the help of Builder class
		this.whitePieces=calculateActivePiece(this.gameBoard,Alliance.WHITE);
	    this.blackPieces=calculateActivePiece(this.gameBoard,Alliance.BLACK);
	    final Collection<Move> whiteStandardLegalMoves=calculateLegalMoves(this.whitePieces);
	    final Collection<Move> blackStandardLegalMoves=calculateLegalMoves(this.blackPieces);
	    this.whitePlayer=new WhitePlayer(this ,whiteStandardLegalMoves ,blackStandardLegalMoves);
	    this.blackPlayer=new BlackPlayer(this ,whiteStandardLegalMoves,blackStandardLegalMoves);
	    this.currentPlayer=builder.nextMoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);
	    
	
	}
	
	@Override
	//How a Board look like when turned into String
	public String toString() 
	{
		  final StringBuilder builder =new StringBuilder();
		  for(int i=0;i<BoardUtils.NUM_TILES;i++){
			  final String tileText=this.gameBoard.get(i).toString();
			  builder.append(String.format("%3s",tileText));
			  if((i+1) % BoardUtils.NUM_TILES_PER_ROW==0){
				  builder.append("\n");
			  }
		  }
		  return builder.toString();
	}
	public Player whitePlayer(){
		return this.whitePlayer;
	}
	public Player blackPlayer(){
		return this.blackPlayer;
	}
	public Player currentPlayer(){
		return this.currentPlayer;
	}
	
	
	
	
	
	
	
	public Collection<Piece> getBlackPieces(){
		return this.blackPieces;
	}
	public Collection<Piece> getWhitePieces(){
		return this.whitePieces;
	}
	
     /*I am passing collection of pieces and 
	 * just want to keep track of its legal moves
	 */
	private Collection<Move> calculateLegalMoves(Collection<Piece> pieces)  
	{
		// TODO Auto-generated method stub
		//making ArrayList of legal Moves pieces white and black
		final List<Move> legalMoves =new ArrayList<>(); 
		for(final Piece piece :pieces)
		{
			/*calculate all legal moves on the board 
			 * and put it into the legalMoves ArrayList
			 */
			legalMoves.addAll(piece.calculateLegalMove(this));
			
		}
	 return ImmutableList.copyOf(legalMoves);
		
	}
	private static Collection<Piece> calculateActivePiece(final List<Tile> gameBoard,
			final Alliance alliance) 
	{
		final List<Piece> activePiece=new ArrayList<>();
		for(final Tile tile:gameBoard){
			if(tile.isTileOccupied()){
				final Piece piece=tile.getPiece();
				if(piece.getPieceAlliance()==alliance){
					activePiece.add(piece);
				}
			}
		}

		return ImmutableList.copyOf(activePiece);
	}


	public Tile getTile(int tileCoordinate) 
	{
		
		return gameBoard.get(tileCoordinate);
	}
	
	
	
	
	public static Board createStandardBoard() //Going to create Initial state of Chess Board
	{
		 Builder builder=new Builder();
		//BLACK LAYOUT
		builder.setPiece(new Rook(Alliance.BLACK,0));
		builder.setPiece(new Knight(Alliance.BLACK,1));
		builder.setPiece(new Bishop(Alliance.BLACK,2));
		builder.setPiece(new Queen(Alliance.BLACK,3));
		builder.setPiece(new King(Alliance.BLACK,4));
		builder.setPiece(new Bishop(Alliance.BLACK,5));
		builder.setPiece(new Knight(Alliance.BLACK,6));
		builder.setPiece(new Rook(Alliance.BLACK,7));
		builder.setPiece(new Pawn(Alliance.BLACK,8));
		builder.setPiece(new Pawn(Alliance.BLACK,9));
		builder.setPiece(new Pawn(Alliance.BLACK,10));
		builder.setPiece(new Pawn(Alliance.BLACK,11));
		builder.setPiece(new Pawn(Alliance.BLACK,12));
		builder.setPiece(new Pawn(Alliance.BLACK,13));
		builder.setPiece(new Pawn(Alliance.BLACK,14));
		builder.setPiece(new Pawn(Alliance.BLACK,15));
		
		//WHITE-LAYOUT
		builder.setPiece(new Pawn(Alliance.WHITE,48));
		builder.setPiece(new Pawn(Alliance.WHITE,49));
		builder.setPiece(new Pawn(Alliance.WHITE,50));
		builder.setPiece(new Pawn(Alliance.WHITE,51));
		builder.setPiece(new Pawn(Alliance.WHITE,52));
		builder.setPiece(new Pawn(Alliance.WHITE,53));
		builder.setPiece(new Pawn(Alliance.WHITE,54));
		builder.setPiece(new Pawn(Alliance.WHITE,55));
		builder.setPiece(new Rook(Alliance.WHITE,56));
		builder.setPiece(new Knight(Alliance.WHITE,57));
		builder.setPiece(new Bishop(Alliance.WHITE,58));
		builder.setPiece(new Queen(Alliance.WHITE,59));
		builder.setPiece(new King(Alliance.WHITE,60));
		builder.setPiece(new Bishop(Alliance.WHITE,61));
		builder.setPiece(new Knight(Alliance.WHITE,62));
		builder.setPiece(new Rook(Alliance.WHITE,63));
	
		builder.setMoveMaker(Alliance.WHITE); //white is ready to move
		return builder.build();
	}
	private static List<Tile> createGameBoard(final Builder builder)
	{
		final Tile[] tiles =new Tile[BoardUtils.NUM_TILES]; //going to create an array of 64
		
		for(int i=0;i<BoardUtils.NUM_TILES;i++) //loop through all the position
		{
			tiles[i]=Tile.createTile(i, builder.boardConfig.get(i)); //Going to map configure the Piece to the Map
		}
		return ImmutableList.copyOf(tiles);
	}
	
	

	
	
	//This sub - class is going to help for instance of class
   public static class Builder  
	  {
		/* Going to build Board Configuration 
		 * i.e Mapping Tile ID to given piece 
		 */
		Map<Integer,Piece> boardConfig;
		/*we need to keep track of nextMove ..
		 *  Which Player is going to move
		 */
		Alliance nextMoveMaker;
		Pawn enPassantPawn;
		
		public Builder()
		{
			this.boardConfig=new HashMap<>(33,1.0f);
			
		}
		public Builder setPiece(final Piece piece)
		{
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		public Builder setMoveMaker(final Alliance nextMoveMaker) //for setting the move
		{
			this.nextMoveMaker=nextMoveMaker;
			return this;
		}
		public Board build()
		{
			return new Board(this);
		}
		public void setEnPassantPawn(Pawn enPassantPawn) {
			// TODO Auto-generated method stub
			this.enPassantPawn=enPassantPawn;
		}
	}





public Iterable<Move> getAllLegalMoves() {
	// TODO Auto-generated method stub
	return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),this.blackPlayer.getLegalMoves()));
}












	
	
	

}
