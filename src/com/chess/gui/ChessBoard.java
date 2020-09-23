package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.StandardEvaluator;
import com.chess.pgn.FENUtilities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static javax.swing.JDialog.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class ChessBoard extends Observable {

    // frame and panels
    private final JFrame gameFrame;
    private final MoveHistoryPanel moveHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final GameSetup gameSetup;

    private Board chessBoard; // create the GUI

    // Variables for moving pieces
    private Square sourceSquare; // Square of selected piece
    private Square destinationSquare; // Square which selected piece will be moved
    private Piece selectedPiece; // Piece that player selected
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    private Move computerMove;


    // Dimensions
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(10,10);

    // Square Colors
    private  final Color lightColor = Color.lightGray;
    private final Color darkColor = Color.decode("#3CB043");

    //piece icon path
    private static String defaultPieceImagesPath = "images/pieces/default/";

    private int searchDepth; // search depth

    private static final ChessBoard INSTANCE = new ChessBoard(); // single instance of chessboard at a time

    private ChessBoard() {
        this.gameFrame = new JFrame("ChessGame");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar chessMenuBar = createChessMenuBar(); //add menu bar
        this.gameFrame.setJMenuBar(chessMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION); // set size of frame
        this.chessBoard = Board.createStartingBoard(); // set up board
        this.moveHistoryPanel = new MoveHistoryPanel(); // create move log
        this.takenPiecesPanel = new TakenPiecesPanel(); // create panel for taken pieces
        this.boardPanel = new BoardPanel(); //create board panel
        this.moveLog = new MoveLog(); // create the move log
        this.addObserver(new ChessGameAIObserver()); // add the observer
        this.gameSetup = new GameSetup(this.gameFrame, true); // set up the game options. Centered to the frame and Modal.
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true; // highlight legal moves will be defaulted to true
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST); // taken pieces will show up on the left
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER); //center panel
        this.gameFrame.add(this.moveHistoryPanel, BorderLayout.EAST); // move log will show up on the right
        center(this.gameFrame);
        setDefaultLookAndFeelDecorated(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit program when closed
        this.gameFrame.setVisible(true); // make frame visible
        this.searchDepth = gameSetup.getSearchDepth();
    }

    public static ChessBoard get(){
        return INSTANCE; // access to the class
    }

    public void show(){
         ChessBoard.get().getMoveLog().clear();
         ChessBoard.get().getMoveHistoryPanel().redo(chessBoard, ChessBoard.get().getMoveLog());
         ChessBoard.get().getTakenPiecesPanel().redo(ChessBoard.get().getMoveLog());
         ChessBoard.get().getBoardPanel().drawBoard(ChessBoard.get().getChessBoard());
    }

    private GameSetup getGameSetup(){
        return this.gameSetup;
    }

    private Board getChessBoard(){
        return this.chessBoard;
    }

    private JMenuBar createChessMenuBar(){
        final JMenuBar chessMenuBar = new JMenuBar();
        chessMenuBar.add(createFileMenu());
        chessMenuBar.add(createPreferenceMenu());
        chessMenuBar.add(createOptionMenu());
        return chessMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        // add menu items
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(e -> System.out.println("Open PGN File"));
        fileMenu.add(openPGN);

        final JMenuItem loadFEN = new JMenuItem("Load FEN");
        loadFEN.addActionListener(e -> {
            String fenString = JOptionPane.showInputDialog("Enter FEN");
            if(fenString != null){
                newGame();
                chessBoard = FENUtilities.createGameFromFEN(fenString);
                ChessBoard.get().getBoardPanel().drawBoard(chessBoard);
            }
        });
        fileMenu.add(loadFEN);

        final JMenuItem getFEN = new JMenuItem("Get FEN");
        //getFEN.addActionListener(e -> System.out.println(FENUtilities.createFEN(chessBoard)));
        getFEN.addActionListener(e -> {

            String fen = FENUtilities.createFEN(chessBoard);

            // Display FEN and ask user if they want to copy it to the clipboard
            String[] options = {"Copy", "Cancel"};
            int answer = JOptionPane.showOptionDialog(
                    gameFrame,
                    fen,
                    "Copy FEN",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if(answer == 0){
                StringSelection stringSelection = new StringSelection(fen);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(gameFrame, "FEN copied to clipboard");
            }
        });
        fileMenu.add(getFEN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit"); // add a menu option to exit program
        exitMenuItem.addActionListener(e -> System.exit(0));

        fileMenu.add(exitMenuItem);


        return fileMenu;
    }

    private JMenu createPreferenceMenu() {
        final JMenu preferenceMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite(); // traverse the board the opposite way
            boardPanel.drawBoard(chessBoard); // redraw the board flipped
        });
        preferenceMenu.add(flipBoardMenuItem);

        preferenceMenu.addSeparator();

        final JCheckBoxMenuItem legalMoveHighLightCheckbox = new JCheckBoxMenuItem("Highlight legal Moves", true);

        legalMoveHighLightCheckbox.addActionListener(e -> highlightLegalMoves = legalMoveHighLightCheckbox.isSelected());

        preferenceMenu.add(legalMoveHighLightCheckbox);

        return preferenceMenu;
    }

    // create an option menu
    private JMenu createOptionMenu(){
        final JMenu optionsMenu = new JMenu("Options");

        final JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(e -> newGame());
        optionsMenu.add(newGameMenuItem);

       /* final JMenuItem resetPositionMenuItem = new JMenuItem("Reset Position");
        resetPositionMenuItem.addActionListener(e -> resetBoard());
        optionsMenu.add(resetPositionMenuItem);

        */

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo Move");
        undoMoveMenuItem.addActionListener(e -> {
            if(ChessBoard.get().getMoveLog().size() > 0){
                undoMove();
            }
        });
        optionsMenu.add(undoMoveMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board");
        evaluateBoardMenuItem.addActionListener(e -> System.out.println(StandardEvaluator.get().evalDetails(chessBoard, gameSetup.getSearchDepth())));
        optionsMenu.add(evaluateBoardMenuItem);

        setupGameMenuItem.addActionListener(e -> {
            ChessBoard.get().getGameSetup().promptUser();
            ChessBoard.get().setupUpdate(ChessBoard.get().getGameSetup());
            // make move, then notify the AI to make its move. Do this by using the Observer Pattern
        });

        optionsMenu.add(setupGameMenuItem);
        return optionsMenu;
    }

    private void setupUpdate(GameSetup gameSetup){
        // use observer (extended by the class)
        setChanged(); // something changed on the board
        notifyObservers(gameSetup); // notify the observers so the next move can be made
    }

    // create an observer to observe the observable
    private static class ChessGameAIObserver implements Observer{

        @Override
        public void update(Observable o, Object arg) {

            if(ChessBoard.get().getGameSetup().isAIPlayer(ChessBoard.get().getChessBoard().currentPlayer()) &&
                    !ChessBoard.get().getChessBoard().currentPlayer().isCheckmate() &&
                    !ChessBoard.get().getChessBoard().currentPlayer().isStalemate()) {
                //create AI thread
                //execute AI work

                AIBrain brain = new AIBrain();
                brain.execute();
            }
            // Game over dialogs

            if(ChessBoard.get().getChessBoard().currentPlayer().isCheckmate()){
                JOptionPane.showMessageDialog(ChessBoard.get().gameFrame, "Game Over - CHECKMATE " +
                        ChessBoard.get().getChessBoard().otherPlayerString() + " Wins");
            }
            if(ChessBoard.get().getChessBoard().currentPlayer().isStalemate()){
                JOptionPane.showMessageDialog(ChessBoard.get().gameFrame, "Game Over - DRAW " +
                        ChessBoard.get().getChessBoard().currentPlayerString()  + " is sin stalemate");
            }
        }
    }

    public void updateChessBoard(Board board){
        this.chessBoard = board;
    }

    public void updateComputerMove(Move move){
        this.computerMove = move;
    }

    // Undo a move
    private void undoMove() {
        final Move lastMove = ChessBoard.get().getMoveLog().removeMove(ChessBoard.get().getMoveLog().size() - 1);
        this.chessBoard = this.chessBoard.currentPlayer().undoMove(lastMove).getBoard();
        this.computerMove = null;
        ChessBoard.get().getMoveLog().removeMove(lastMove);
        ChessBoard.get().getMoveHistoryPanel().redo(chessBoard, ChessBoard.get().getMoveLog());
        ChessBoard.get().getTakenPiecesPanel().redo(ChessBoard.get().getMoveLog());
        ChessBoard.get().getBoardPanel().drawBoard(chessBoard);
    }

    // Reset the game to a new game
    public void newGame(){
        for(int i = ChessBoard.get().getMoveLog().size() - 1; i >= 0; i--){
            Move lastMove = ChessBoard.get().getMoveLog().removeMove(ChessBoard.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.currentPlayer().undoMove(lastMove).getBoard();
        }
    }
/*
    public void resetBoard(){
        for(int i = ChessBoard.get().getMoveLog().size() - 1; i >= 0; i--){
            Move lastMove = ChessBoard.get().getMoveLog().removeMove(ChessBoard.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.currentPlayer().undoMove(lastMove).getBoard();
        }
    }
*/
    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private MoveHistoryPanel getMoveHistoryPanel(){
        return this.moveHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel(){
        return this.takenPiecesPanel;
    }

    private BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

    private void moveMadeUpdate(PlayerType playerType){
        setChanged();
        notifyObservers(playerType);
    }

    private int getSearchDepth(){
        return this.searchDepth;
    }

    private static class AIBrain extends SwingWorker<Move, String>{

        private AIBrain(){
            // Nothing goes here
        }

        @Override
        protected Move doInBackground() {
            final MoveStrategy miniMax = new MiniMax(ChessBoard.get().getSearchDepth());
            final Move bestMove = miniMax.execute(ChessBoard.get().getChessBoard());
            return bestMove;
        }
        // What to do once it is done:
        @Override
        public void done(){
            try{
                final Move bestMove = get();
                ChessBoard.get().updateComputerMove(bestMove);
                ChessBoard.get().updateChessBoard(ChessBoard.get().getChessBoard().currentPlayer().makeMove(bestMove).getBoard()); // make the best move
                // update the GUI
                ChessBoard.get().getMoveLog().addMove(bestMove);
                ChessBoard.get().getMoveHistoryPanel().redo(ChessBoard.get().getChessBoard(), ChessBoard.get().getMoveLog());
                ChessBoard.get().getTakenPiecesPanel().redo(ChessBoard.get().getMoveLog());
                ChessBoard.get().getBoardPanel().drawBoard(ChessBoard.get().getChessBoard());
                ChessBoard.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch(InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
    }

    // Create the board panel
    private class BoardPanel extends JPanel{
        final List<SquarePanel> squares;

        BoardPanel(){
            super(new GridLayout(BoardUtils.NUM_SQUARES_PER_ROW, BoardUtils.NUM_SQUARES_PER_ROW));
            this.squares = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
                final SquarePanel squarePanel = new SquarePanel(this, i);
                this.squares.add(squarePanel);
                add(squarePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board){
            removeAll();
            for(SquarePanel squarePanel : boardDirection.traverse(squares)){
                squarePanel.drawSquare(board);
                add(squarePanel);
            }
            validate();
            repaint();
        }
    }

    // Move Log -- Keep track of  moves made
    public static class MoveLog{

        private final List<Move> moves;

        MoveLog(){
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves(){
            return this.moves;
        }

        public void addMove(Move move){
            this.moves.add(move);
        }

        public int size(){
            return this.moves.size();
        }

        public void clear(){
            this.moves.clear();
        }

        public boolean removeMove(final Move move){
            return this.moves.remove(move);
        }

        public Move removeMove(int index) {
            return this.moves.remove(index);
        }
    }

    // Create the squares
    class SquarePanel extends JPanel{

        private final int squareId;

        SquarePanel(final BoardPanel boardPanel, final int squareId){
            super(new GridBagLayout());
            this.squareId = squareId;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColor();
            assignSquarePieceIcon(chessBoard);

            // Adding ability to move  pieces
            // Each square will have a mouse listener which will listen for a click event on that square
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Cancel a piece selection if right mouse is clicked
                    if(isRightMouseButton(e)) {
                        clearSquares();
                    }
                    // If no piece is selected on a left click
                    else if (isLeftMouseButton(e)){
                        if(sourceSquare == null){
                            // Get the id of the square selected -- first clicked square
                            sourceSquare = chessBoard.getSquare(squareId);
                            selectedPiece = sourceSquare.getPiece();
                            if(selectedPiece == null){
                                sourceSquare = null; // no piece was selected on that square
                            }
                        }
                        // If the source tile is not null -- make the move
                        else{
                            destinationSquare = chessBoard.getSquare(squareId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceSquare.getCoordinate(),
                                                                          destinationSquare.getCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()){
                             chessBoard = transition.getBoard();
                             moveLog.addMove(move);
                            }
                            clearSquares();
                        }
                        SwingUtilities.invokeLater(() -> {
                            moveHistoryPanel.redo(chessBoard, moveLog);
                            takenPiecesPanel.redo(moveLog);

                            // notify the AI to make the move after human moves
                            if(gameSetup.isAIPlayer(chessBoard.currentPlayer())){
                                ChessBoard.get().moveMadeUpdate(PlayerType.HUMAN);
                            }
                            boardPanel.drawBoard(chessBoard);
                        });
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            validate();
        }

        /* Add the piece icons to the board
        Pieces named based on this convention:
        Path to folder +
        Color of Piece (W/B) +
        Type of Piece (K/Q/B/N/R) +
        .gif  */

        private void assignSquarePieceIcon(final Board board){
            this.removeAll();
            if(board.getSquare(this.squareId).isSquareOccupied()){
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagesPath +
                                    board.getSquare(this.squareId).getPiece().getPieceColor().toString().substring(0,1) +
                                    board.getSquare(this.squareId).getPiece().toString() +
                                    ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Method to highlight legal moves for selected piece
        private void highlightLegalMoves(final Board board){
            if(highlightLegalMoves){
                for(final Move move : pieceLegalMoves(board)) {
                    if(move.getDestinationCoordinate() == this.squareId){
                        try{
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("images/misc/legal.png")))));
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // Get the legal moves of the selected piece
        private Collection<Move> pieceLegalMoves(Board board) {
            if(selectedPiece != null && selectedPiece.getPieceColor() == board.currentPlayer().getColor()) {
                // handle castling
                if(selectedPiece.getPieceType().isKing() && selectedPiece.isFirstMove()){
                    final List<Move> includesCastleMoves = new ArrayList<>();
                    includesCastleMoves.addAll(board.currentPlayer().calculateKingCastles(board.currentPlayer().getLegalMoves(),
                            board.currentPlayer().getOpponent().getLegalMoves()));
                    return ImmutableList.copyOf(Iterables.concat(selectedPiece.calculateLegalMoves(board), includesCastleMoves));
                }
                return selectedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        //method to fill squares white or black
        private void assignSquareColor() {
            // Even rows color light color, odd rows color dark color
            // split by every other row because the algorithm flips around depending on the starting tile of the row
            if(BoardUtils.EIGHTH_ROW[this.squareId] ||
                    BoardUtils.SIXTH_ROW[this.squareId] ||
                    BoardUtils.FOURTH_ROW[this.squareId] ||
                    BoardUtils.SECOND_ROW[this.squareId]) {
                if (this.squareId % 2 == 0) {
                    setBackground(lightColor);
                } else {
                    setBackground(darkColor);
                }
            }
            else if(BoardUtils.SEVENTH_ROW[this.squareId] ||
                        BoardUtils.FIFTH_ROW[this.squareId] ||
                        BoardUtils.THIRD_ROW[this.squareId] ||
                        BoardUtils.FIRST_ROW[this.squareId]){
                    if(this.squareId %2 == 0){
                        setBackground(darkColor);
                    }
                    else{
                        setBackground(lightColor);
                    }
            }
        }

        public void drawSquare(Board board){
            assignSquareColor();
            assignSquarePieceIcon(board);
            highlightLegalMoves(board);
            validate();
            repaint();
        }

        public void clearSquares(){
            sourceSquare = null;
            selectedPiece = null;

        }
    }

    private static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }

    public enum BoardDirection{

        NORMAL{
            @Override
            List<SquarePanel> traverse(List<SquarePanel> squares) {
                return squares; // traverse the squares in normal order
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            List<SquarePanel> traverse(List<SquarePanel> squares) {
                return Lists.reverse(squares); // reverse the square order in reverse if the board is flipped
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<SquarePanel> traverse(final List<SquarePanel> squares); // traverse the squares
        abstract BoardDirection opposite(); // return the other type of board
    }
}