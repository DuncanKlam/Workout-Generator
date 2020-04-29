package domain;

import java.util.List;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "exercise")
public class Exercise {

    @DatabaseField(id = true)
    public String name;

    @DatabaseField()
    public int sets;

    @DatabaseField()
    public int reps;

    @DatabaseField()
    public String unit;

    @DatabaseField()
    public String muscleGroupsString;

    @DatabaseField()
    public String description;

    public int tier;

    public List<String> muscleGroups;

    public Exercise(){ }

    public Exercise(String name, int sets, int reps, String unit, List muscleGroups, String description){
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.unit = unit;
        this.muscleGroups = muscleGroups;
        this.muscleGroupsString = muscleGroups.toString();
        this.description = description;
        this.tier = muscleGroups.size();
    }

    @Override
    public boolean equals(Object o){
        if(o == null)                return false;
        if(!(o instanceof Exercise)) return false;

        Exercise other = (Exercise) o;
        if(! (this.name.contains(other.name)))      return false;
        if(! (this.sets == other.sets))      return false;
        if(! (this.reps == other.reps))      return false;
        if(! (this.unit.contains(other.unit)))      return false;

        return true;
    }

    @Override
    public String toString(){
        if(name == null){
            return " ";
        }
        return name + '\n' +
                "Sets:'" + sets + '\n' +
                "Reps='" + reps + '\n' +
                "Unit='" + unit + '\n' +
                "MuscleGroups='" + muscleGroupsString;
    }


    public String[] getMuscleGroupsArray() {
        String[] muscleGroupArray = new String[muscleGroups.size()];
        return muscleGroups.toArray(muscleGroupArray);
    }
}
