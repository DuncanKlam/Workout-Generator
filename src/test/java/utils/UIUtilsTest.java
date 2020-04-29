package utils;

import UI.CheckBoxTableModel;
import domain.Exercise;
import domain.Workout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UIUtilsTest {

    Exercise testExercise;
    Workout testWorkout;
    LinkedList<Exercise> exercises;
    LinkedList<Exercise> emptyExercises;
    LinkedList<Exercise> nullExercises;
    LinkedList<Workout> workouts;
    LinkedList<Workout> emptyWorkouts;
    LinkedList<Workout> nullWorkouts;
    DefaultTableModel exerciseTableData;
    DefaultTableModel workoutTableData;
    DefaultTableModel workoutTable;
    CheckBoxTableModel checkBoxTable;
    JCheckBox arms;
    JCheckBox legs;
    JCheckBox[] checkBoxes;

    @BeforeEach
    void setUp() {
        exercises = new LinkedList<>();
        emptyExercises = new LinkedList<>();
        nullExercises = null;
        workouts = new LinkedList<>();
        emptyWorkouts = new LinkedList<>();
        nullWorkouts = null;

        testExercise = new Exercise("Test Exercise",1,1,"test", List.of("Bicep", "Tricep", "Quads"), "This is a test exercise");
        exercises.add(testExercise);

        testWorkout = new Workout("Test Workout", exercises, "High");
        workouts.add(testWorkout);

        String[] columns = {"Name","Sets", "Reps", "Unit"};
        Object[][] data = {};
        exerciseTableData = new DefaultTableModel(data,columns);

        String[] columnsW = {"Name","Intensity","Duration"};
        Object[][] dataW = {};
        workoutTableData = new DefaultTableModel(dataW,columnsW);

        String[] columnsWT = {"Name", "Sets", "Reps", "Unit", "Tier"};
        Object[][] dataWT = {};
        workoutTable = new DefaultTableModel(dataWT, columnsWT);

        checkBoxTable = new CheckBoxTableModel(exercises);

        arms = new JCheckBox("arms");
        legs = new JCheckBox("legs");

        checkBoxes = new JCheckBox[]{arms, legs};
    }

    @Test
    void updateExerciseTableDataNullList() {
        String result = UIUtils.updateExerciseTableData(exerciseTableData, nullExercises);
        assertEquals("List is null", result);
        assertEquals(0, exerciseTableData.getRowCount());
    }

    @Test
    void updateExerciseTableDataEmptyList(){
        String result = UIUtils.updateExerciseTableData(exerciseTableData, emptyExercises);
        assertEquals("List is empty", result);
        assertEquals(0, exerciseTableData.getRowCount());
    }

    @Test
    void updateExerciseTableDataOneEntry() {
        String result = UIUtils.updateExerciseTableData(exerciseTableData, exercises);
        assertEquals("Success", result);
        assertEquals(1, exerciseTableData.getRowCount());
    }

    @Test
    void updateExerciseTableDataNullEntries() {
        exercises.add(new Exercise());
        exercises.add(testExercise);
        exercises.add(new Exercise());

        String result = UIUtils.updateExerciseTableData(exerciseTableData, exercises);
        assertEquals("Success", result);
        assertEquals(4, exerciseTableData.getRowCount());
    }

    @Test
    void updateWorkoutTableDataNullList() {
        String result = UIUtils.updateWorkoutTableData(workoutTableData, nullWorkouts);
        assertEquals("List is null", result);
        assertEquals(0, workoutTableData.getRowCount());
    }

    @Test
    void updateWorkoutTableEmptyList(){
        String result = UIUtils.updateWorkoutTableData(workoutTableData, emptyWorkouts);
        assertEquals("List is empty", result);
        assertEquals(0, workoutTableData.getRowCount());
    }

    @Test
    void updateWorkoutTableDataOneEntry() {
        String result = UIUtils.updateWorkoutTableData(workoutTableData, workouts);
        assertEquals("Success", result);
        assertEquals(1, workoutTableData.getRowCount());
    }

    @Test
    void updateWorkoutTableDataNullEntries(){
        workouts.add(new Workout());
        workouts.add(testWorkout);
        workouts.add(new Workout());

        String result = UIUtils.updateWorkoutTableData(workoutTableData, workouts);
        assertEquals("Success", result);
        assertEquals(4,workoutTableData.getRowCount());
    }

    @Test
    void makeWorkoutTableWithNullList() {
        String result = UIUtils.makeWorkoutTable(workoutTable, nullExercises);
        assertEquals("List is null", result);
    }

    @Test
    void makeWorkoutTableWithEmptyList() {
        String result = UIUtils.makeWorkoutTable(workoutTable, emptyExercises);
        assertEquals("List is empty", result);
    }

    @Test
    void makeWorkoutTableWithRegList() {
        String result = UIUtils.makeWorkoutTable(workoutTable, exercises);
        assertEquals("Success", result);
    }

    @Test
    void getMusclesListNoBoxesChecked() {
        LinkedList<String> result = UIUtils.getMusclesList(checkBoxes);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    void getMusclesListOneBoxChecked() {
        arms.setSelected(true);
        LinkedList<String> result = UIUtils.getMusclesList(checkBoxes);
        assertEquals(1, result.size());
        assertTrue(result.contains("arms"));
    }

    @Test
    void getMusclesListAllBoxesChecked() {
        arms.setSelected(true);
        legs.setSelected(true);
        LinkedList<String> result = UIUtils.getMusclesList(checkBoxes);
        assertEquals(2, result.size());
        assertTrue(result.contains("arms"));
        assertTrue(result.contains("legs"));
    }

    @AfterEach
    void tearDown() {

    }
}