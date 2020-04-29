package utils;

import domain.Exercise;
import domain.Workout;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilsTest {

    DatabaseUtils databaseUtils;

    List<String> list = List.of("test", "test2", "test3");

    Exercise testExercise = new Exercise("test",1,1,"test",list, "test exercise");

    List<Exercise> exerciseList = List.of(testExercise, testExercise, testExercise);

    Workout testWorkout = new Workout("test", exerciseList,"Low");
    Workout w = null;

    DatabaseUtilsTest(){
        try {
            testWorkout.setTimeEnded(LocalTime.now().plusMinutes(2));
            w = new Workout(testWorkout);
            databaseUtils = new DatabaseUtils();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void readExerciseTableIsNotNull() {
        assertNotNull(databaseUtils.readExerciseTable());
    }

    @org.junit.jupiter.api.Test
    void readWorkoutTableIsNotNull() {
        assertNotNull(databaseUtils.readWorkoutTable());
    }

    @org.junit.jupiter.api.Test
    void exerciseTableContainsAtLeastOneEntry() {
        assertTrue(databaseUtils.readExerciseTable().get(0).getClass() == Exercise.class);
    }

    @org.junit.jupiter.api.Test
    void workoutTableContainsAtLeastOneEntry() {
        testWorkout.setTimeEnded(LocalTime.now().plusMinutes(2));
        Workout w = new Workout(testWorkout);
        databaseUtils.addWorkoutToDatabase(w);
        assertTrue(databaseUtils.readWorkoutTable().get(0).getClass() == Workout.class);
    }

    @org.junit.jupiter.api.Test
    void exerciseTableAcceptsInputAndDeletion() {
        databaseUtils.addExerciseToDatabase(testExercise);
        assertTrue(databaseUtils.readExerciseTable().contains(testExercise));
        assertEquals("Database Delete: Success", databaseUtils.deleteSingleExercise(testExercise.name));
    }

    @org.junit.jupiter.api.Test
    void workoutTableAcceptsInputAndDeletion() {
        databaseUtils.addWorkoutToDatabase(w);
        assertTrue(databaseUtils.readWorkoutTable().contains(w));
        assertEquals("Database Delete: Success", databaseUtils.deleteSingleWorkout(w.title,w.duration));
    }

    @org.junit.jupiter.api.Test
    void clearWorkoutTableOfTestWorkouts(){
        assertEquals("Success", databaseUtils.clearWorkoutDatabaseOfSpecificWorkout(w));
    }
}