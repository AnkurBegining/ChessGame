// I created game in java
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Piece.PieceType;

import java.util.*;
public class Ankur
{

	public static void main(String[] args) 
	{
		Scanner scan=new Scanner(System.in);
		Board board=Board.createStandardBoard();
		
		System.out.println("Do remember while playing ::::\nWhite is for Player 1 and \nBlack is for Player 2");
		System.out.println("Capital letter is for White player ");
		System.out.println("Small letter is for Black player");
		System.out.println(board);
		int count=0;
		for(;count<30;count++){
			if(count%2 == 0)
				System.out.println("Player 1::white::turn to play");
			if(count%2 != 0)
				System.out.println("Player 2::black::turn to play");
			System.out.println("Enter the current destination");
		    int currentCoordinate=scan.nextInt();
		    System.out.println("Enter the final destination");
		    int destinationCoordinate=scan.nextInt();
		    
		    
		    Move movefactory=MoveFactory.createMove(board, currentCoordinate, destinationCoordinate);
		    board=movefactory.execute();
		    System.out.println(board);
		    
		    
		    
		}
	 }

	
}


