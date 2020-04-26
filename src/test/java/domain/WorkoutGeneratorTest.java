package domain;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutGeneratorTest {

    WorkoutGenerator workoutGenerator = new WorkoutGenerator();


    @Test
    void deleteWorkout() {
        workoutGenerator.deleteWorkout("Full Body/0h 0m 20s");
    }
}