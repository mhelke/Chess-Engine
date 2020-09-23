package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {

    // Pieces on the board
    private final List<Square> chessBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final Pawn enPassantPawn; // Pawn that could be taken en passant

    // Players
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    public Board(final BoardBuilder builder) {
        this.chessBoard = createChessBoard(builder);
        this.whitePieces = calculateActivePieces(this.chessBoard, Color.WHITE);
        this.blackPieces = calculateActivePieces(this.chessBoard, Color.BLACK);
        this.enPassantPawn = builder.enPassantPawn;

        // Calculate the legal moves
        final Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackLegalMoves =  calculateLegalMoves(this.blackPieces);

        // Add players
        this.whitePlayer = new WhitePlayer(this, whiteLegalMoves, blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteLegalMoves, blackLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {

        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    // Return pieces currently on board for a specific color
    private static Collection<Piece> calculateActivePieces(final List<Square> chessBoard, final Color color) {

        final List<Piece> activePieces = new ArrayList<>();
        for(final Square square : chessBoard){
            if(square.isSquareOccupied()) {
                final Piece piece = square.getPiece();
                if(piece.getPieceColor() == color) { //get the pieces of a certain color
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Square getSquare(int squareCoordinate) {
        return chessBoard.get(squareCoordinate);
    }//end getSquare

    private static List<Square> createChessBoard(final BoardBuilder builder){
        final Square[] squares = new Square[BoardUtils.NUM_SQUARES];

        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(squares);
    }

    //set up starting position
    public static Board createStartingBoard(){
        final BoardBuilder builder = new BoardBuilder();
        // Black Setup
        builder.setPiece(new Rook(Color.BLACK,0));
        builder.setPiece(new Knight(Color.BLACK,1));
        builder.setPiece(new Bishop(Color.BLACK,2));
        builder.setPiece(new Queen(Color.BLACK,3));
        builder.setPiece(new King(Color.BLACK,4, true, true));
        builder.setPiece(new Bishop(Color.BLACK,5));
        builder.setPiece(new Knight(Color.BLACK,6));
        builder.setPiece(new Rook(Color.BLACK,7));
        builder.setPiece(new Pawn(Color.BLACK,8));
        builder.setPiece(new Pawn(Color.BLACK,9));
        builder.setPiece(new Pawn(Color.BLACK,10));
        builder.setPiece(new Pawn(Color.BLACK,11));
        builder.setPiece(new Pawn(Color.BLACK,12));
        builder.setPiece(new Pawn(Color.BLACK,13));
        builder.setPiece(new Pawn(Color.BLACK,14));
        builder.setPiece(new Pawn(Color.BLACK,15));
        // White Setup
        builder.setPiece(new Pawn(Color.WHITE,48));
        builder.setPiece(new Pawn(Color.WHITE,49));
        builder.setPiece(new Pawn(Color.WHITE,50));
        builder.setPiece(new Pawn(Color.WHITE,51));
        builder.setPiece(new Pawn(Color.WHITE,52));
        builder.setPiece(new Pawn(Color.WHITE,53));
        builder.setPiece(new Pawn(Color.WHITE,54));
        builder.setPiece(new Pawn(Color.WHITE,55));
        builder.setPiece(new Rook(Color.WHITE,56));
        builder.setPiece(new Knight(Color.WHITE,57));
        builder.setPiece(new Bishop(Color.WHITE,58));
        builder.setPiece(new Queen(Color.WHITE,59));
        builder.setPiece(new King(Color.WHITE,60, true, true));
        builder.setPiece(new Bishop(Color.WHITE,61));
        builder.setPiece(new Knight(Color.WHITE,62));
        builder.setPiece(new Rook(Color.WHITE,63));

        // White to move first
        builder.setMoveMaker(Color.WHITE);

        return builder.build();
    }

    // Get the players
    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player blackPlayer(){
        return this.blackPlayer;
    }

    public Player currentPlayer(){
        return this.currentPlayer;
    }

    // Return a string for the current player
    public String currentPlayerString(){
        if(this.currentPlayer.getColor() == Color.WHITE){
            return "White Player";
        }
        else{
            return "Black Player";
        }
    }

    // Return a string for the opponent
    public String otherPlayerString(){
        if(this.currentPlayer.getOpponent().getColor() == Color.WHITE){
            return "White Player";
        }
        else{
            return "Black Player";
        }
    }

    public Pawn getEnPassantPawn(){
        return this.enPassantPawn;
    }

    // Get white and black pieces
    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    // Concat white and black player's legal moves to get all legal moves on the board
    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    public Collection<Piece> getAllPieces(){
            List<Piece> pieces = new ArrayList<>();
            pieces.addAll(whitePieces);
            pieces.addAll(blackPieces);
            return pieces;

    }

    //Board as a String
    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();

        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            final String squareText = this.chessBoard.get(i).toString();
            builder.append(String.format("%3s", squareText));
            if((i + 1) % BoardUtils.NUM_SQUARES_PER_ROW == 0)  {
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}