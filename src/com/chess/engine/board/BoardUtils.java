package com.chess.engine.board;

public class BoardUtils {
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);//array of boolean which Represent 1st column 
	public static final boolean[] SECOND_COLUMN = initColumn(1);//array of boolean which Represent 2nd column
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);//array of boolean which Represent 7th column
	public static final boolean[] EIGTH_COLUMN = initColumn(7);//array of boolean which Represent 8th column
	public static final boolean[] SECOND_ROW=initRow(8);
	public static final boolean[] SEVENTH_ROW=initRow(48);
	public static final int NUM_TILES=64;
	public static final int NUM_TILES_PER_ROW=8;
	public static final int NUM_TILES_PER_COLUMN=8;
	
	private static boolean[] initColumn(int columnNumber) {
		final boolean[] column=new boolean[NUM_TILES];
		do{
			column[columnNumber]=true;
			columnNumber+=NUM_TILES_PER_ROW; //Using trick to go to tile of that column only
		}while(columnNumber<NUM_TILES);
		return column;
		
	}
	
	
	
	
	
	private static boolean[] initRow(int rowNumber) 
	{
		final boolean[] row =new boolean[NUM_TILES];
		do{
			row[rowNumber]=true;
			rowNumber++;
			
		  }while( (rowNumber % NUM_TILES_PER_ROW) !=0);
		return row;
	}





	private BoardUtils()
	{
		throw new RuntimeException("You can't instantiate me");
	}

	

	public static boolean isValidTileCoorinate(final int candidateDestinationCoordinate) //Checking valid moves of Knight on tile..Is it not going outside of Chess-Tile Board.
	{
	
		return candidateDestinationCoordinate>=0 && candidateDestinationCoordinate<NUM_TILES;
	} 

}
