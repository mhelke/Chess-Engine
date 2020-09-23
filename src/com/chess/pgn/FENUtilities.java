package com.chess.pgn;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardBuilder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.*;

public class FENUtilities {

    private FENUtilities(){ /*Nothing here*/}

    public static Board createGameFromFEN(String fen){
        return convertFEN(fen);
    }

    private static Board convertFEN(String fen) {
        final String[] splitFEN = fen.trim().split(" "); // split FEN into sections (pieces, player, castle, en passant, time)
        final BoardBuilder builder = new BoardBuilder();
        // get castle options from FEN String
        boolean whiteKingSideCastle = whiteKingSideCastle(splitFEN[2]);
        boolean whiteQueenSideCastle = whiteQueenSideCastle(splitFEN[2]);
        boolean blackKingSideCastle = blackKingSideCastle(splitFEN[2]);
        boolean blackQueenSideCastle = blackQueenSideCastle(splitFEN[2]);

        // Format fen to place pieces on board
        String boardConfig = splitFEN[0];
        char[] boardSquares = boardConfig.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();

        // Set up the en passant pawn
        int enPassantCoordinate = BoardUtils.getCoordinateAtPosition(splitFEN[3]);
        int enPassantPawnCoordinate = enPassantCoordinate + (8)*moveMaker(splitFEN[1]).getOppositeDirection();

        // Create pieces for board
        int i = 0;
        while(i < boardSquares.length){
            switch (boardSquares[i]) {
                case 'r':
                    builder.setPiece(new Rook(Color.BLACK, i));
                    i++;
                    break;
                case 'n':
                    builder.setPiece(new Knight(Color.BLACK, i));
                    i++;
                    break;
                case 'b':
                    builder.setPiece(new Bishop(Color.BLACK, i));
                    i++;
                    break;
                case 'q':
                    builder.setPiece(new Queen(Color.BLACK, i));
                    i++;
                    break;
                case 'k':
                    builder.setPiece(new King(Color.BLACK, i, blackKingSideCastle, blackQueenSideCastle));
                    i++;
                    break;
                case 'p':
                    if(i == enPassantPawnCoordinate){ // handles en passant pawns
                        Pawn ep = new Pawn(Color.BLACK, i);
                        builder.setPiece(ep);
                        builder.setEnPassantPawn(ep);
                        i++;
                    }
                    else{
                        builder.setPiece(new Pawn(Color.BLACK, i));
                        i++;
                    }
                    break;
                case 'R':
                    builder.setPiece(new Rook(Color.WHITE, i));
                    i++;
                    break;
                case 'N':
                    builder.setPiece(new Knight(Color.WHITE, i));
                    i++;
                    break;
                case 'B':
                    builder.setPiece(new Bishop(Color.WHITE, i));
                    i++;
                    break;
                case 'Q':
                    builder.setPiece(new Queen(Color.WHITE, i));
                    i++;
                    break;
                case 'K':
                    builder.setPiece(new King(Color.WHITE, i, whiteKingSideCastle, whiteQueenSideCastle));
                    i++;
                    break;
                case 'P':
                    if(i == enPassantPawnCoordinate){ // handles en passant pawns
                        Pawn ep = new Pawn(Color.WHITE, i);
                        builder.setPiece(ep);
                        builder.setEnPassantPawn(ep);
                        i++;
                    }
                    else{
                        builder.setPiece(new Pawn(Color.WHITE, i));
                        i++;
                    }
                    break;
                case '-':
                    i++;
                    break;
                default:
                    throw new RuntimeException("Invalid FEN String: " + boardConfig);
            }
        }
        builder.setMoveMaker(moveMaker(splitFEN[1]));
        return builder.build();
    }

    // Method to set the current player
    private static Color moveMaker(String currentPlayerString) {
        switch (currentPlayerString){
            case "w":
                return Color.WHITE;
            case "b":
                return Color.BLACK;
            default:
                throw new RuntimeException("Invalid FEN String: " + currentPlayerString);
        }
    }

    // Methods to calculate castling
    private static boolean whiteKingSideCastle(String fenCastleString){
        return fenCastleString.contains("K");
    }

    private static boolean whiteQueenSideCastle(String fenCastleString){
        return fenCastleString.contains("Q");
    }

    private static boolean blackKingSideCastle(String fenCastleString){
        return fenCastleString.contains("k");
    }

    private static boolean blackQueenSideCastle(String fenCastleString){
        return fenCastleString.contains("q");
    }


    public static String createFEN(Board board){
        return  calculateBoard(board) + " " +
                calculateCurrentPlayerText(board) + " " +
                calculateCastleText(board) + " " +
                calculateEnPassantSquare(board) + " 0 1";
    }

    private static String calculateBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            String squareText = board.getSquare(i).toString();
            builder.append(squareText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");

        // replace multiple empty squares to fit FEN format
        return builder.toString().replaceAll("--------", "8")
                                 .replaceAll("-------", "7")
                                 .replaceAll("------", "6")
                                 .replaceAll("-----", "5")
                                 .replaceAll("----", "4")
                                 .replaceAll("---", "3")
                                 .replaceAll("--", "2")
                                 .replaceAll("-", "1");
    }

    private static String calculateEnPassantSquare(Board board) {
        Pawn enPassantPawn = board.getEnPassantPawn();
        if(enPassantPawn != null){
            // calculate the square behind the en passant pawn (square you would capture on)
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() + (8)*enPassantPawn.getPieceColor().getOppositeDirection());
        }
        else{
            return "-";
        }
    }

    private static String calculateCastleText(Board board) {
        StringBuilder builder = new StringBuilder();
        if(board.whitePlayer().isKingSideCastleCapable()){
            builder.append("K");
        }
        if(board.whitePlayer().isQueenSideCastleCapable()){
            builder.append("Q");
        }
        if(board.blackPlayer().isKingSideCastleCapable()){
            builder.append("k");
        }
        if(board.blackPlayer().isQueenSideCastleCapable()){
            builder.append("q");
        }
        String result = builder.toString();

        if(result.isEmpty()){
            return "-";
        }
        else{
            return result;
        }
    }

    private static String calculateCurrentPlayerText(Board board) {
        return board.currentPlayer().toString();
    }

}
