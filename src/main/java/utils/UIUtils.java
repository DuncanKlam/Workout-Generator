package utils;

import UI.CheckBoxTableModel;
import domain.Exercise;
import domain.Workout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class UIUtils extends JFrame{

    private static JPanel panel;

    public UIUtils(JPanel panel){
       this.panel = panel;
    }


    public static void updateTableData(DefaultTableModel tableData, LinkedList<Exercise> exerciseList) {
        if (exerciseList == null){
            return;
        }
        if (tableData.getRowCount() == 0){
            for (Exercise e : exerciseList){
                tableData.addRow(new Object[]{e.name, e.sets, e.reps, e.unit});
            }
        }
        else{
            clearTable(tableData);
            for (Exercise e : exerciseList){
                tableData.addRow(new Object[]{e.name, e.sets, e.reps, e.unit});
            }
        }
        tableData.fireTableStructureChanged();
    }

    public static void updateWorkoutTableData(DefaultTableModel tableData, LinkedList<Workout> workoutList) {
        if (workoutList == null){
            return;
        }
        if (tableData.getRowCount() == 0){
            for (Workout w : workoutList){
                tableData.addRow(new Object[]{w.title, w.intensityNumber, w.duration});
            }
        }
        else{
            clearTable(tableData);
            for (Workout w : workoutList){
                tableData.addRow(new Object[]{w.title, w.intensityNumber, w.duration});
            }
        }
        tableData.fireTableStructureChanged();
    }

    private static void clearTable(DefaultTableModel tableData) {
        int rows = tableData.getRowCount();
        for (int i = 0; i<rows; i++){
            tableData.removeRow(0);
        }
    }

    public static void makeWorkoutTable(DefaultTableModel tableData, LinkedList<Exercise> exerciseList) {
        if (exerciseList == null){
            return;
        }
        for (Exercise e : exerciseList) {
            tableData.addRow(new Object[]{e.name, e.sets, e.reps, e.unit, e.tier});
        }
        tableData.fireTableStructureChanged();
    }

    public static void makeCheckTable(CheckBoxTableModel checkTable, List<Exercise> exerciseList) {
        if (exerciseList == null){
            return;
        }
        for(int i=0;i<exerciseList.size();i++){
            checkTable.data[i] = makeCheckTableObjectArray(exerciseList.get(i),checkTable.getColumnCount());
        }
        checkTable.fireTableStructureChanged();
    }

    private static Object[] makeCheckTableObjectArray(Exercise exercise, int columnCount) {
        Object[] tableObject = new Object[columnCount];
        tableObject[0] = exercise.name;
        for(int i=1;i<columnCount;i++){
            if(i <= exercise.sets){
                tableObject[i] = false;
            } else {
                tableObject[i] = true;
            }
        }
        return tableObject;
    }
    
    public static boolean everyCheckIsTrue(CheckBoxTableModel table) {
        if((Boolean)table.getValueAt(table.getRowCount()-1,table.getColumnCount()-1) == true){
            return true;
        }
        for(int i=1;i<table.getColumnCount();i++){
            for(int k=0;k<table.getRowCount();k++){
                if ((Boolean)table.getValueAt(k,i) == false){
                    return false;
                }
            }
        }
        return true;
    }

    public String getTextFromComponent(int index){
        Component component = panel.getComponent(index);
        JTextField textField = (JTextField) component;
        return textField.getText();
    }

    public int getIntFromComponent(int index) {
        Component component = panel.getComponent(index);
        JTextField textField = (JTextField) component;
        if(textField.getText().isEmpty()){
            return 0;
        }
        return Integer.parseInt(textField.getText());
    }

    public LinkedList<String> getMusclesList(JCheckBox[] checkBoxes) {
        LinkedList<String> muscleGroupsUsed = new LinkedList<>();
        for(JCheckBox box: checkBoxes){
            if(box.isSelected()){
                muscleGroupsUsed.add(box.getText());
            }
        }
        return muscleGroupsUsed;
    }



}
