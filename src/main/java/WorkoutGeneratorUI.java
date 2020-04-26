import UI.*;
import domain.Exercise;
import domain.WorkoutGenerator;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkoutGeneratorUI extends JFrame implements ActionListener{

    public WorkoutGeneratorUI(){


        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 13)));

        Dimension buttonDimension = new Dimension(135,30);
        Dimension workoutDimension = new Dimension(225,50);
        JPanel panel = new JPanel();
        WorkoutGenerator workoutGenerator = new WorkoutGenerator();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        panel.setName("Workout Generator Graphical Interface");
        setContentPane(panel);

        //WORKOUT TABLE LABEL
        JLabel tableLabel = new JLabel("Exercises in database");
        var tableLabelConstraints = new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(20, 20, 20, 20), 0, 10);
        panel.add(tableLabel,tableLabelConstraints);

        //WORKOUT TABLE
        String[] columns = {"Name","Sets", "Reps", "Unit"};
        Object[][] data = {};
        DefaultTableModel tableData = new DefaultTableModel(data,columns);
        JTable workoutTable = new JTable(tableData);
        var workoutTableConstraints = new GridBagConstraints(1, 1, 3, 6, 2, 2, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 300);
        JScrollPane JScrollPane = new JScrollPane(workoutTable);
        panel.add(JScrollPane,workoutTableConstraints);

        //ADD EXERCISE BUTTON
        JButton addExerciseButton = new JButton("Add Exercise");
        addExerciseButton.setMinimumSize(buttonDimension);
        addExerciseButton.addActionListener(e -> {
            ExerciseMakerUI maker = new ExerciseMakerUI();
            maker.makeExercise();
        });

        //EDIT EXERCISE BUTTON
        JButton editExerciseButton = new JButton("Edit Exercise");
        editExerciseButton.setMinimumSize(buttonDimension);
        editExerciseButton.addActionListener(e -> {
            int selectedRow = workoutTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Choose exercise in list to edit");
            } else {
                String name = (String) workoutTable.getValueAt(selectedRow, 0);
                Exercise exerciseToEdit = workoutGenerator.getExercise(name);
                ExerciseEditorUI eventSource = new ExerciseEditorUI();
                eventSource.editExercise(exerciseToEdit);
                workoutGenerator.deleteExercise(exerciseToEdit.name);
                workoutGenerator.addExercise(exerciseToEdit);
            }
        });

        //DELETE EXERCISE BUTTON
        JButton deleteExerciseButton = new JButton("Delete Exercise");
        deleteExerciseButton.setMinimumSize(buttonDimension);
        deleteExerciseButton.addActionListener(e ->{
            int selectedRow = workoutTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Choose exercise in list to delete");
            } else {
                String name = (String) workoutTable.getValueAt(selectedRow, 0);
                String response = workoutGenerator.deleteExercise(name);
                JOptionPane.showMessageDialog(panel, response);
            }
        });

        //VIEW EXERCISE BUTTON
        JButton viewExerciseButton = new JButton("View Complete Exercise");
        viewExerciseButton.setMinimumSize(workoutDimension);
        viewExerciseButton.addActionListener(e -> {
            int selectedRow = workoutTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Choose exercise in list to view");
            }
            else{
                String name = (String) workoutTable.getValueAt(selectedRow,0);
                Exercise exerciseToView = workoutGenerator.getExercise(name);
                new ExerciseViewerUI(exerciseToView);
            }
        });

        JSplitPane addEditSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, addExerciseButton, editExerciseButton);
        JSplitPane deleteViewSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, deleteExerciseButton, viewExerciseButton);
        JSplitPane buttonPanesSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, addEditSplitPane, deleteViewSplitPane);
        var scrollPaneConstraints = new GridBagConstraints(1, 4, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(20, 20, 20, 20), 30, 0);
        panel.add(buttonPanesSplitPane, scrollPaneConstraints);



        //DROPDOWN TYPE OF WORKOUT
        String[] typeOfWorkout = {"Full Body", "Upper Body", "Lower Body", "Tricep/Bicep/Chest", "Abs/Back"};
        JComboBox workoutTypeBox = new JComboBox(typeOfWorkout);
        workoutTypeBox.setSelectedIndex(0);
        workoutTypeBox.setFont(new Font("Dialog", Font.PLAIN, 22));
        var workoutTypeBoxConstraints = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(20, 20, 1, 20), 0, 0);
        workoutTypeBox.setMinimumSize(workoutDimension);
        panel.add(workoutTypeBox, workoutTypeBoxConstraints);

        //DROPDOWN INTENSITY
        String[] intensityLevels = {"High","Medium","Low","Extra Low"};
        JComboBox intensityBox = new JComboBox(intensityLevels);
        intensityBox.setSelectedIndex(1);
        intensityBox.setFont(new Font("Dialog", Font.PLAIN, 22));
        var intensityBoxConstraints = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 20, 1, 20), 0, 0);
        intensityBox.setMinimumSize(workoutDimension);
        panel.add(intensityBox, intensityBoxConstraints);

        //GENERATE WORKOUT BUTTON
        JButton generateWorkoutButton = new JButton("Generate Workout");
        generateWorkoutButton.setFont(new Font("Dialog", Font.PLAIN, 22));
        var generateWorkoutButtonConstraints = new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 20, 1, 20), 0, 0);
        generateWorkoutButton.setMinimumSize(workoutDimension);
        panel.add(generateWorkoutButton,generateWorkoutButtonConstraints);
        generateWorkoutButton.addActionListener(e -> {
            String workoutType = (String) workoutTypeBox.getSelectedItem();
            String intensity = (String) intensityBox.getSelectedItem();
            WorkoutUI eventSource = new WorkoutUI();
            eventSource.showWorkout(workoutGenerator.generateWorkout(workoutType, intensity));
            eventSource.addObserver(workoutGenerator::addWorkout);
        });

        //WORKOUT TABLE VIEWER
        JButton tableViewer = new JButton("Workout Table Viewer");
        var tableViewerConstraints = new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(1, 20, 20, 20), 0, 0);
        tableViewer.setMinimumSize(workoutDimension);
        panel.add(tableViewer,tableViewerConstraints);
        tableViewer.addActionListener(e ->{
            WorkoutTableViewerUI workoutViewer = new WorkoutTableViewerUI();
            workoutViewer.showWorkoutTable();
            workoutViewer.addObserver(workoutGenerator::deleteWorkout);
        });

        //Application Window
        UIUtils.updateTableData(tableData, workoutGenerator.getExerciseList());
        setPreferredSize(new Dimension(1000, 650));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        new WorkoutGeneratorUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
