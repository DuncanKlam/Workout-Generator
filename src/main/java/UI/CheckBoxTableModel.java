package UI;

import domain.Exercise;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CheckBoxTableModel extends AbstractTableModel {

    public String[] columns;
    public Object[][] data;

    public CheckBoxTableModel(List<Exercise> eList) {
        int mostSets = getMostSets(eList);
        columns = new String[mostSets+1];
        columns[0] = "Name";
        for(int i=1; i<mostSets+1;i++){
            columns[i] = "Rep " + i;
        }
        data = new Object[eList.size()][];
    }

    private int getMostSets(List<Exercise> eList) {
        int mostSets = 1;
        for(Exercise e : eList){
            if(e.sets>mostSets){
                mostSets=e.sets;
            }
        }
        return mostSets;
    }

    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex > 0) {
            if (aValue instanceof Boolean) {
                data[rowIndex][columnIndex] = aValue;
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col > 0 && getValueAt(row,col) != null) {
            return true;
        } else {
            return false;
        }
    }
}
