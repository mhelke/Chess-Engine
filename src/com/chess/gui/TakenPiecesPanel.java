package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.ChessBoard.MoveLog;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TakenPiecesPanel extends JPanel {

    // 2 Panels - for white and black
    private final JPanel northPanel;
    private final JPanel southPanel;

    // Panel settings
    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    private static String defaultPieceImagesPath = "images/pieces/default/"; // location of images

    public TakenPiecesPanel(){
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8,2)); // 2 pieces per row, 8 rows
        this.southPanel = new JPanel(new GridLayout(8,2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog){
        this.northPanel.removeAll();
        this.southPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        // Go through all moves and look for attack moves
        // Save  the  piece  that was taken on  that move
        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceColor().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                }
                else if(takenPiece.getPieceColor().isBlack()){
                    blackTakenPieces.add(takenPiece);
                }
                else{
                    throw new RuntimeException("It should not be possible to get here");
                }
            }
        }

        // Compare values of taken pieces to sort them
        Collections.sort(whiteTakenPieces, (o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));

        Collections.sort(blackTakenPieces, (o1, o2) -> Ints.compare(o1.getPieceValue(), o2.getPieceValue()));

        // Draw the taken pieces on the screen

        for(final Piece takenPiece : whiteTakenPieces){
            try{
                final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                        takenPiece.getPieceColor().toString().substring(0,1) + "" + takenPiece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.northPanel.add(imageLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        }

        // Black Pieces
        for(final Piece takenPiece : blackTakenPieces){
            try{
                final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath +
                        takenPiece.getPieceColor().toString().substring(0,1) + "" + takenPiece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 15, icon.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
}
