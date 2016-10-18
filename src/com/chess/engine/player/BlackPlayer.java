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

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		// TODO Auto-generated method stub
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		// I will return back here and make some progress later
		return this.board.whitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		final List<Move> kingCastles=new ArrayList<>();
		if(this.playerKing.isFirstMove() && !this.isInCheck()) //If King first move and it is not in check
		{
			//Black King side castle
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) //If both coordinate 61 and 62 are not occupied then only we can interchange King and rook 
			{
				final Tile rookTile =this.board.getTile(7);//King side rook
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
					
					if(Player.calculateAttackOnTile(5, opponentsLegals).isEmpty() &&
							Player.calculateAttackOnTile(6, opponentsLegals).isEmpty() &&
							rookTile.getPiece().getPieceType().isRook())
					{
						//Ankur You have to come back here..."For adding castleMove"
						kingCastles.add(new Move.KingSideCastleMove(this.board,this.playerKing,
                                                                    6, 
                                                                    (Rook)rookTile.getPiece(), 
                                                                    rookTile.getTileCoordinate(),
                                                                    5));
						
					}
				}
			}
			//Black Queen side castle
			if(!this.board.getTile(1).isTileOccupied() &&
			   !this.board.getTile(2).isTileOccupied() && 
			   !this.board.getTile(3).isTileOccupied())
			{
				final Tile rookTile =this.board.getTile(0);//Queen side rook
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					//Ankur You have to come back here..."For adding queenSide castleMove"
					kingCastles.add(new Move.QueenSideCastleMove(this.board,this.playerKing,
                                                                2, 
                                                                (Rook)rookTile.getPiece(), 
                                                                rookTile.getTileCoordinate(),
                                                                3));
				}
				
				
			}
			
		}
		
		
		
		return ImmutableList.copyOf(kingCastles);
	}

}
