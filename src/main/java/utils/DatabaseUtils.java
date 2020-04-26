package utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import domain.Exercise;
import domain.Workout;

import java.sql.SQLException;
import java.util.LinkedList;


public class DatabaseUtils {

    private Dao<Exercise, String> exerciseDao;
    private Dao<Workout, String> workoutDao;


    public DatabaseUtils() throws SQLException {
        var connectionSource = new JdbcConnectionSource("jdbc:sqlite:exercise.db");

        TableUtils.createTableIfNotExists(connectionSource, Exercise.class);
        exerciseDao = DaoManager.createDao(connectionSource, Exercise.class);

        TableUtils.createTableIfNotExists(connectionSource, Workout.class);
        workoutDao = DaoManager.createDao(connectionSource, Workout.class);
    }


    public String addExerciseToDatabase(Exercise exercise){
        try{
            exerciseDao.create(exercise);
            return "Success";
        } catch (SQLException e){
            return "Item exists in database";
        }
    }

    public LinkedList<Exercise> readExerciseTable(){
        try {
            var allItems = exerciseDao.queryForAll();
            return new LinkedList<>(allItems);
        } catch(SQLException e){
            e.getErrorCode();
            return new LinkedList<>();
        }
    }

    public String deleteSingleExercise(String identifier){
        try {
            var customQuery = exerciseDao.queryBuilder().where().eq("name", identifier).prepare();
            var itemToDelete = exerciseDao.query(customQuery);
            if (itemToDelete.isEmpty()) {
                return "Not on database";
            } else {
                exerciseDao.delete(itemToDelete);
                return "Database Delete: Success";
            }
        } catch (SQLException e){
            return "Not on database";
        }
    }

    public void clearExerciseDatabase() throws SQLException {
        exerciseDao.delete(exerciseDao.queryForAll());
    }

    public String addWorkoutToDatabase(Workout w){
        Workout transferWorkout = new Workout(w);
       for(int i=0;i<w.exerciseList.size();i++){
           transferWorkout.setExercise(w.exerciseList.get(i),w.exArr[i]);
       }
       for(int i=w.exerciseList.size();i<8;i++){
           transferWorkout.setExercise(new Exercise(), w.exArr[i]);
       }
        try{
            workoutDao.create(transferWorkout);
            return "Success";
        } catch (SQLException e){
            return "Item exists in database";
        }
    }

    public LinkedList<Workout> readWorkoutTable(){
        try {
            var allWorkouts = workoutDao.queryForAll();
            return new LinkedList<>(allWorkouts);
        } catch(SQLException e){
            e.getErrorCode();
            return new LinkedList<>();
        }
    }

    public String deleteSingleWorkout(String title, String duration){
        try {
            Workout workoutToDelete = null;
            var allWorkouts = workoutDao.queryForAll();
            for(Workout w: allWorkouts){
                if(w.title.contentEquals(title) && w.duration.contentEquals(duration)){
                    workoutToDelete = w;
                }
            }
            workoutDao.delete(workoutToDelete);
            return "Database Delete: Success";
        } catch (SQLException e){
            return "Not on database";
        }
    }

    public void clearWorkoutDatabase() throws SQLException {
        workoutDao.delete(workoutDao.queryForAll());
    }
}
