package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player {

	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves)
	{
		super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() 
	{
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		// I will return back here and will again make the code
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		final List<Move> kingCastles=new ArrayList<>();
		if(this.playerKing.isFirstMove() && !this.isInCheck()) //If King first move and it is not in check
		{
			//White King side castle
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) //If both coordinate 61 and 62 are not occupied then only we can interchange King and rook 
			{
				final Tile rookTile =this.board.getTile(63);//King side rook
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
					
					if(Player.calculateAttackOnTile(61, opponentsLegals).isEmpty() &&
							Player.calculateAttackOnTile(62, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook())
					{
						//Ankur You have to come back here..."For adding castleMove"
						kingCastles.add(new Move.KingSideCastleMove(this.board,this.playerKing,
								                                    62, 
								                                    (Rook)rookTile.getPiece(), 
								                                    rookTile.getTileCoordinate(),
								                                    61));
						
						
					}
				}
			}
			//White Queen side castle
			if(!this.board.getTile(59).isTileOccupied() &&
			   !this.board.getTile(58).isTileOccupied() && 
			   !this.board.getTile(57).isTileOccupied())
			{
				final Tile rookTile =this.board.getTile(56);//Queen side rook
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					//Ankur You have to come back here..."For adding queenSide castleMove"
					kingCastles.add(new Move.QueenSideCastleMove(this.board,this.playerKing,
                                                                 58, 
                                                                 (Rook)rookTile.getPiece(), 
                                                                 rookTile.getTileCoordinate(),
                                                                 59));
				}
				
				
			}
			
		}
		
		
		
		return ImmutableList.copyOf(kingCastles);
	}

}
