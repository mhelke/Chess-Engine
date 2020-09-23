package com.chess.gui;

import com.chess.engine.Color;
import com.chess.engine.player.Player;

import javax.swing.*;
import java.awt.*;

public class GameSetup extends JDialog {

    private PlayerType whitePlayer;
    private PlayerType blackPlayer;
    private JSpinner searchDepthSpinner;

    private static final String HUMAN_TEXT = "Human";
    private static final String COMPUTER_TEXT = "Computer";

    GameSetup(final JFrame frame, final boolean modal) {
        super(frame, modal);
        final JPanel chessPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton whiteHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton whiteComputerButton = new JRadioButton(COMPUTER_TEXT);
        final JRadioButton blackHumanButton = new JRadioButton(HUMAN_TEXT);
        final JRadioButton blackComputerButton = new JRadioButton(COMPUTER_TEXT);
        whiteHumanButton.setActionCommand(HUMAN_TEXT);
        final ButtonGroup whiteGroup = new ButtonGroup(); // group for options for white player
        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
        whiteHumanButton.setSelected(true); // set human as default for white player

        final ButtonGroup blackGroup = new ButtonGroup(); // group for options for black player
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
        blackHumanButton.setSelected(true); // set human as default for black player

        getContentPane().add(chessPanel);
        chessPanel.add(new JLabel("White"));
        chessPanel.add(whiteHumanButton);
        chessPanel.add(whiteComputerButton);
        chessPanel.add(new JLabel("Black"));
        chessPanel.add(blackHumanButton);
        chessPanel.add(blackComputerButton);

        chessPanel.add(new JLabel("Search"));
        this.searchDepthSpinner = addLabeledSpinner(chessPanel, new SpinnerNumberModel(4, 0, Integer.MAX_VALUE, 1));
        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            whitePlayer = whiteComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            blackPlayer = blackComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            GameSetup.this.setVisible(false);
        });

        cancelButton.addActionListener(e -> {
            System.out.println("Cancel");
            GameSetup.this.setVisible(false);
        });

        chessPanel.add(cancelButton);
        chessPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

        void promptUser(){
            setVisible(true);
            repaint();
        }

        boolean isAIPlayer(Player player){
            if(player.getColor() == Color.WHITE) {
                return getWhitePlayerType() == PlayerType.COMPUTER;
            }
            return getBlackPlayerType() == PlayerType.COMPUTER;
        }

        private PlayerType getWhitePlayerType() {
            return this.whitePlayer;
        }

        private PlayerType getBlackPlayerType() {
            return this.blackPlayer;
        }

        private static JSpinner addLabeledSpinner(final Container c, final SpinnerModel model) {
            final JLabel l = new JLabel("Search Depth");
            c.add(l);
            final JSpinner spinner = new JSpinner(model);
            l.setLabelFor(spinner);
            c.add(spinner);
            return spinner;
        }

        public int getSearchDepth(){
        return (Integer)this.searchDepthSpinner.getValue();
    }
}