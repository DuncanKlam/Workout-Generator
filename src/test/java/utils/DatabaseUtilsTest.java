package utils;

import domain.Exercise;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilsTest {

    DatabaseUtils databaseUtils = new DatabaseUtils();

    DatabaseUtilsTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    void addExerciseToDatabase() {
    }

    @org.junit.jupiter.api.Test
    void readDatabaseIsNotNull() {
        assertNotNull(databaseUtils.readExerciseTable());
    }

    @org.junit.jupiter.api.Test
    void readDatabaseContainsExercises() {
        assertTrue(databaseUtils.readExerciseTable().get(0).getClass() == Exercise.class);
    }

    @org.junit.jupiter.api.Test
    void deleteSingleExercise() {
    }

    @org.junit.jupiter.api.Test
    void clearDatabase() {
    }

    @Test
    void deleteSingleWorkout() {
        databaseUtils.deleteSingleWorkout("Full Body", "0h 0m 2s");
    }
}