package domain;

import utils.DatabaseUtils;

import javax.swing.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorkoutGenerator {

    public DatabaseUtils databaseUtils;

    public WorkoutGenerator() {
        try {
            this.databaseUtils = new DatabaseUtils();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String addExercise(Exercise e){
        return databaseUtils.addExerciseToDatabase(e);
    }

    public String addWorkout(Workout w){ return  databaseUtils.addWorkoutToDatabase(w); }

    public String deleteExercise(String name){
        return databaseUtils.deleteSingleExercise(name);
    }

    public String deleteWorkout(String response) {
        int index = response.indexOf("Z");
        String title = response.substring(0,index);
        String duration = response.substring(index+1);
        return  databaseUtils.deleteSingleWorkout(title, duration);
    }

    public LinkedList<Exercise> getExerciseList(){
        LinkedList<Exercise> exercises = new LinkedList<>();
        for(Exercise e : databaseUtils.readExerciseTable()){
            e.muscleGroups = decodeMuscleGroups(e.muscleGroupsString);
            e.tier = e.muscleGroups.size();
            exercises.add(e);
        }
        return exercises;
    }

    public Exercise getExercise(String name) {
        Exercise exercise = null;
        for(Exercise e : getExerciseList()){
            if(name.contains(e.name)){
                exercise = e;
            }
        }
        return exercise;
    }

    private List<String> decodeMuscleGroups(String muscleGroupsString) {
        String[] muscleGroups = {"Chest","Upper Back","Lower Back","Abs","Bicep","Tricep","Shoulder","Calves","Quads","Glutes"};
        LinkedList<String> groupList = new LinkedList<>();
        for(String muscleGroup : muscleGroups){
            if(muscleGroupsString.contains(muscleGroup)){
                groupList.add(muscleGroup);
            }
        }
        return groupList;
    }

    public Workout generateWorkout(String workoutType, String intensityString) {
        String[] musclesToRetrieve = getMusclesToRetrieve(workoutType);
        int intensity = 2 * getIntensityFromString(intensityString);
        List<Exercise> possibleExercises = getPossibleExercises(musclesToRetrieve);
        List<Integer> indexValues = getIndexValuesToRetrieve(intensity, possibleExercises.size());
        List<Exercise> workoutExercises = getRandomExercisesForWorkout(possibleExercises, indexValues);
        return new Workout(workoutType, workoutExercises, intensityString);
    }

    private List<Exercise> getRandomExercisesForWorkout(List<Exercise> possibleExercises, List<Integer> indexValues) {
        List<Exercise> e = new LinkedList<>();
        for(Integer index : indexValues){
            e.add(possibleExercises.get(index));
        }
        sortByNumberOfEngagedMuscles(e);
        return e;
    }


    private void sortByNumberOfEngagedMuscles(List<Exercise> unordered) {
        Exercise temp;
        Exercise[] exercises = new Exercise[unordered.size()];
        exercises = unordered.toArray(exercises);
        for (int i=0;i<exercises.length;i++){
            for(int k=0;k<exercises.length;k++){
                if(exercises[i].tier < exercises[k].tier){
                    temp = exercises[i];
                    exercises[i] = exercises[k];
                    exercises[k] = temp;
                }
            }
        }
        unordered.clear();
        for (int j=exercises.length-1;j>-1;j--){
            unordered.add(exercises[j]);
        }
    }

    private List<Integer> getIndexValuesToRetrieve(int intensity, int size) {
        List<Integer> indexValuesToRetrieve = new LinkedList<>();
        while(indexValuesToRetrieve.size()<intensity){
            int indexValue = getRandomNumberInRange(size-1);
            if (!indexValuesToRetrieve.contains(indexValue)){
                indexValuesToRetrieve.add(indexValue);
            }
        }
        return indexValuesToRetrieve;
    }

    private int getRandomNumberInRange(int max) {
        Random r = new Random();
        return r.nextInt((max) + 1);
    }

    private List<Exercise> getPossibleExercises(String[] musclesToRetrieve) {
        List<Exercise> exercises = new LinkedList<>();
        for (String s : musclesToRetrieve){
            for (Exercise e : getExerciseList()){
                if(e.muscleGroupsString.contains(s) && !exercises.contains(e)){
                    exercises.add(e);
                }
            }
        }
        return exercises;
    }

    private int getIntensityFromString(String intensityString) {
        if(intensityString.contentEquals("High")){
            return 4;
        } else if(intensityString.contentEquals("Medium")){
            return 3;
        } else if(intensityString.contentEquals("Low")){
            return 2;
        } else {
            return 1;
        }
    }

    private String[] getMusclesToRetrieve(String workoutType) {
        if (workoutType.contains("Full")){
            return new String[]{"Chest","Upper Back","Lower Back","Abs","Bicep","Tricep","Shoulder","Calves","Quads","Glutes"};
        } else if(workoutType.contains("Upper")){
            return new String[]{"Chest", "Upper Back", "Lower Back", "Abs", "Bicep", "Tricep", "Shoulder"};
        } else if(workoutType.contains("Lower")){
            return new String[]{"Calves", "Quads", "Glutes"};
        } else if(workoutType.contains("Chest")){
            return new String[]{"Tricep", "Bicep", "Chest", "Shoulder"};
        } else if(workoutType.contains("Back")){
            return new String[]{"Upper Back","Lower Back","Abs"};
        } else{
            return new String[]{"None"};
        }
    }

}
