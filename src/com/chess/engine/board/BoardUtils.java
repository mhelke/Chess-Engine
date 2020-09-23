package com.chess.engine.board;

import java.util.*;

/******************************************
 * Utility Class to store important values
*******************************************/
public class BoardUtils {

    /*
        Note:
        The code views the board from white's view. So the 1st Column is on the left and the first row is on the bottom
        The rowNum however starts from top to bottom and left to right. So square 0 starts in the top left corner
    */

    // Create columns - pass the id of the square that starts the column
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    // Create rows - pass the id of the square that starts the row

    public static final boolean[] EIGHTH_ROW = initRow(0);
    public static final boolean[] SEVENTH_ROW = initRow(8);
    public static final boolean[] SIXTH_ROW = initRow(16);
    public static final boolean[] FIFTH_ROW = initRow(24);
    public static final boolean[] FOURTH_ROW = initRow(32);
    public static final boolean[] THIRD_ROW = initRow(40);
    public static final boolean[] SECOND_ROW = initRow(48);
    public static final boolean[] FIRST_ROW = initRow(56);

    // Notation variables
    private static final List<String> ALGEBRAIC_NOTATION = initAlgebraicNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();

    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;

    private BoardUtils() {/*Nothing here*/}

    // Create Columns
    private static boolean[] initColumn(int columnNum) {
        final boolean[] column = new boolean[NUM_SQUARES];
        do{
            column[columnNum] = true;
            columnNum+= NUM_SQUARES_PER_ROW;
        } while(columnNum < NUM_SQUARES);
        return column;
    }

    // Create Rows
    private static boolean[] initRow(int rowNum) {
        final boolean[] row = new  boolean[NUM_SQUARES];
        do{
            row[rowNum] = true;
            rowNum++;
        } while(rowNum % NUM_SQUARES_PER_ROW != 0);
        return row;
    }


    // Method to determine if the coordinate is on the board
    public static boolean isValidSquareCoordinate(final int coordinate) {
        return coordinate >=0 && coordinate < NUM_SQUARES;
    }

    // Returns the coordinate given the Algebraic notation
    public static int getCoordinateAtPosition(final String position){
        return POSITION_TO_COORDINATE.get(position);
    }

    // Returns the Algebraic notation of the move
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    // Creates the Algebraic notation of each square
    private static List<String> initAlgebraicNotation() {

        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    // Converts Algebraic notation to the code's coordinates
    private static Map<String, Integer> initPositionToCoordinateMap() {

        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for(int i = 0; i < NUM_SQUARES; i++){
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    public static int squareColor(int squareId){
        if(BoardUtils.EIGHTH_ROW[squareId] ||
                BoardUtils.SIXTH_ROW[squareId] ||
                BoardUtils.FOURTH_ROW[squareId] ||
                BoardUtils.SECOND_ROW[squareId]) {
            if (squareId % 2 == 0) {
                return 0; // light square
            } else {
                return 1; // dark square
            }
        }
        else {
            if(squareId %2 == 0){
                return 1; // dark square
            }
            else{
                return 0; // light square
            }
        }
    }
}