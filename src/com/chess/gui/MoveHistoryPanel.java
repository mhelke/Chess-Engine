package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.gui.ChessBoard.MoveLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveHistoryPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension MOVE_HISTORY_PANEL_DIMENSION = new Dimension(100, 40);

    MoveHistoryPanel(){
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table); // create scroll bar
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(MOVE_HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER); // allow scrolling when pane fills up
        this.setVisible(true);
    }

    public void redo(final Board board, final MoveLog moveLog){
        int currentRow = 0;
        this.model.clear();
        for(final Move move : moveLog.getMoves()){
            final String moveText = move.toString();
            if(move.getMovedPiece().getPieceColor().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            }
            else if(move.getMovedPiece().getPieceColor().isBlack()){
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if(moveLog.getMoves().size() > 0){
            final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1); // last move made
            final String moveText = lastMove.toString();
            if(lastMove.getMovedPiece().getPieceColor().isWhite()){
                this.model.setValueAt(moveText + calculateCheckAndCheckmateHash(board), currentRow, 0);
            }
            else if(lastMove.getMovedPiece().getPieceColor().isBlack()){
                this.model.setValueAt(moveText + calculateCheckAndCheckmateHash(board), currentRow - 1, 1);
            }
        }
        // advancing the scroll bar on the GUI
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    // method  to calculate if the king is in check or checkmate
    private String calculateCheckAndCheckmateHash(final Board board) {
        if(board.currentPlayer().isCheckmate()){
            return "#"; // # means checkmate
        }
        else if(board.currentPlayer().isCheck()){
            return "+"; // + means check
        }
        else{
            return ""; // no extra symbol needs to be added to the notation
        }
    }

    private static class DataModel extends DefaultTableModel {

        private final List<Row> values;
        private static final String[] HEADERS = {"White", "Black"};

        DataModel(){
            this.values= new ArrayList<>();
        }

        public void clear(){
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount(){
            if(this.values == null){
                return 0;
            }
            else{
                return this.values.size();
            }
        }

        @Override
        public int getColumnCount(){
            return HEADERS.length;
        }

        @Override
        public Object getValueAt(int row, int col){
            Row currentRow = this.values.get(row);
            if(col == 0){
                return currentRow.getWhiteMove();
            }
            else if(col == 1){
                return currentRow.getBlackMove();
            }
            else{
                return null;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col){
            final Row currentRow;
            if(this.values.size() <= row){
                currentRow = new Row();
                this.values.add(currentRow);
            }
            else{
                currentRow = this.values.get(row);
            }
            if(col == 0){
                currentRow.setWhiteMove((String)value);
                fireTableRowsInserted(row, row); // add a new row
            }
            else if(col == 1){
                currentRow.setBlackMove((String)value);
                fireTableCellUpdated(row, col);
            }
        }
        @Override
        public Class<?> getColumnClass(int col){
            return Move.class;
        }
        @Override
        public String getColumnName(int col){
            return HEADERS[col];
        }
    }

    // create rows for the moves
    private static class Row {

        private String whiteMove;
        private String blackMove;

        Row(){
            //nothing is here
        }

        public String getWhiteMove(){
            return this.whiteMove;
        }

        public String getBlackMove(){
            return this.blackMove;
        }

        public void setWhiteMove(String whiteMove){
            this.whiteMove = whiteMove;
        }

        public void setBlackMove(String blackMove){
            this.blackMove = blackMove;
        }
    }

}
