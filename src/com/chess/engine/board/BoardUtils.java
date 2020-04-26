package com.chess.engine.board;

// Utility Class for values
public class BoardUtils {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = initRow(8); //square id that begins row 2
    public static final boolean[] SEVENTH_ROW = initRow(48); //square id that begins  row 7

    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;
    //Cannot call the constructor as it is not needed
    private BoardUtils() {}

    //Add 8 to the first row's column to get all the cases
    private static boolean[] initColumn(int columnNum) {
        final boolean[] column = new boolean[NUM_SQUARES];
        do{
            column[columnNum] = true;
            columnNum+= NUM_SQUARES_PER_ROW;
        } while(columnNum < NUM_SQUARES);
        return column;
    }

    //Row exceptions
    private static boolean[] initRow(int rowNum) {
        final boolean[] row = new  boolean[NUM_SQUARES];
        do{
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_SQUARES_PER_ROW != 0);
        return row;
    }




    //Method to determine if the coordinate is on the board
    public static boolean isValidSquareCoordinate(final int coordinate) {
        return coordinate >=0 && coordinate < NUM_SQUARES;
    }//end valid square method
}
