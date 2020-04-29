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


    public static String updateExerciseTableData(DefaultTableModel tableData, LinkedList<Exercise> exerciseList) {
        try {
            if (exerciseList.isEmpty()) {
                return "List is empty";
            }
            if (tableData.getRowCount() != 0) {
                clearTable(tableData);
            }
            String result = addListToExerciseTable(tableData, exerciseList);
            tableData.fireTableStructureChanged();
            return result;
        } catch(NullPointerException e){
            return "List is null";
        }
    }

    private static String addListToExerciseTable(DefaultTableModel tableData, LinkedList<Exercise> exerciseList) {
        try {
            for (Exercise e : exerciseList) {
                tableData.addRow(new Object[]{e.name, e.sets, e.reps, e.unit});
            }
            return "Success";
        } catch(NullPointerException e){
            e.printStackTrace();
            return "Null Pointer Error";
        }
    }

    public static String updateWorkoutTableData(DefaultTableModel tableData, LinkedList<Workout> workoutList) {
        try {
            if (workoutList.isEmpty()) {
                return "List is empty";
            }
            if (tableData.getRowCount() != 0) {
                clearTable(tableData);
            }
            String result = addListToWorkoutTable(tableData, workoutList);
            tableData.fireTableStructureChanged();
            return result;
        } catch(NullPointerException e){
            return "List is null";
        }
    }

    private static String addListToWorkoutTable(DefaultTableModel tableData, LinkedList<Workout> workoutList) {
        try {
            for (Workout w : workoutList) {
                tableData.addRow(new Object[]{w.title, w.intensityNumber, w.duration});
            }
            return "Success";
        } catch(NullPointerException e){
            return "Null Pointer Error";
        }
    }

    private static void clearTable(DefaultTableModel tableData) {
        int rows = tableData.getRowCount();
        for (int i = 0; i<rows; i++){
            tableData.removeRow(0);
        }
    }

    public static String makeWorkoutTable(DefaultTableModel tableData, LinkedList<Exercise> exerciseList) {
        try {
            if (exerciseList.isEmpty()) {
                return "List is empty";
            }
            for (Exercise e : exerciseList) {
                tableData.addRow(new Object[]{e.name, e.sets, e.reps, e.unit, e.tier});
            }
            tableData.fireTableStructureChanged();
            return "Success";
        }
        catch(NullPointerException e){
            return "List is null";
        }
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
        if((Boolean) table.getValueAt(table.getRowCount() - 1, table.getColumnCount() - 1)){
            return true;
        }
        for(int i=1;i<table.getColumnCount();i++){
            for(int k=0;k<table.getRowCount();k++){
                if (!((Boolean) table.getValueAt(k, i))){
                    return false;
                }
            }
        }
        return true;
    }

    public static String getTextFromComponent(JPanel panel, int index){
        Component component = panel.getComponent(index);
        JTextField textField = (JTextField) component;
        return textField.getText();
    }

    public static int getIntFromComponent(JPanel panel, int index) {
        Component component = panel.getComponent(index);
        JTextField textField = (JTextField) component;
        if(textField.getText().isEmpty()){
            return 0;
        }
        return Integer.parseInt(textField.getText());
    }

    public static LinkedList<String> getMusclesList(JCheckBox[] checkBoxes) {
        LinkedList<String> muscleGroupsUsed = new LinkedList<>();
        for(JCheckBox box: checkBoxes){
            if(box.isSelected()){
                muscleGroupsUsed.add(box.getText());
            }
        }
        return muscleGroupsUsed;
    }



}
