package domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@DatabaseTable(tableName = "workout")
public class Workout {


    @DatabaseField()
    public String title;

    @DatabaseField(id = true)
    public String duration;

    @DatabaseField
    private String ex1 = "";

    @DatabaseField
    private String ex2 = "";

    @DatabaseField
    private String ex3 = "";

    @DatabaseField
    private String ex4 = "";

    @DatabaseField
    private String ex5 = "";

    @DatabaseField
    private String ex6 = "";

    @DatabaseField
    private String ex7 = "";

    @DatabaseField
    private String ex8 = "";

    @DatabaseField
    public int intensityNumber = 2;

    public String[] exArr = {"ex1", "ex2", "ex3", "ex4", "ex5", "ex6", "ex7", "ex8"};

    public List<Exercise> exerciseList;
    public String intensity;
    public LocalTime timeStarted;
    public LocalTime timeEnded;

    public Workout(){}

    public Workout(Workout w){
        this.title = w.title;
        this.timeStarted = w.timeStarted;
        this.timeEnded = w.timeEnded;
        this.duration = getWorkoutDuration();
        this.intensityNumber = w.intensityNumber;
    }

    public Workout(String title, List<Exercise> exercises, String intensityString){
        this.title = title;
        this.exerciseList = exercises;
        this.intensity = intensityString;
        this.timeStarted = LocalTime.now();
        this.intensityNumber = exercises.size();
    }

    public String getTitle() {
        return String.format("%s Workout: %s Intensity",title,intensity);
    }

    public void setTimeEnded(LocalTime timeEnded){
        this.timeEnded = timeEnded;
    }

    public String getWorkoutDuration(){
        Duration duration = Duration.between(timeStarted, timeEnded);
        return String.format("%dh %dm %ds",duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    public void setExercise(Exercise e, String ex){
        if(ex.contentEquals("ex1")){
            this.ex1=e.toString();
        }else if(ex.contentEquals("ex2")){
            this.ex2=e.toString();
        }else if(ex.contentEquals("ex3")){
            this.ex3=e.toString();
        }else if(ex.contentEquals("ex4")){
            this.ex4=e.toString();
        }else if(ex.contentEquals("ex5")){
            this.ex5=e.toString();
        }else if(ex.contentEquals("ex6")){
            this.ex6=e.toString();
        }else if(ex.contentEquals("ex7")){
            this.ex7=e.toString();
        }else{
            this.ex8=e.toString();
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null)                return false;
        if(!(o instanceof Workout)) return false;

        Workout other = (Workout) o;
        if(! (this.title.equals(other.title)))      return false;
        if(! (this.duration.equals(other.duration)))      return false;

        return true;
    }
}
