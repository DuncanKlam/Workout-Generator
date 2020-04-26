package UI;


import domain.Exercise;
import domain.Workout;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class WorkoutUI extends JFrame implements ActionListener {

    public WorkoutUI(){ }
        public interface Observer {
        void update(Workout finishedWorkout);
    }

    private final ArrayList<WorkoutUI.Observer> observers = new ArrayList<>();

    private void notifyObservers(Workout finishedWorkout) {
        observers.forEach(observer -> observer.update(finishedWorkout));
    }

    public void addObserver(WorkoutUI.Observer observer) {
        observers.add(observer);
    }

    public void showWorkout(Workout workout) {
        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        panel.setName("Exercise Viewer Interface");
        setContentPane(panel);
        GridBagConstraints tableConstraints = new GridBagConstraints(0, 1, 3, 3, 1, 1, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 1, 5), 0, 150);

        //NAME LABEL
        JLabel nameLabel = new JLabel(workout.getTitle());
        var nameLabelConstraints = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(1, 5, 1, 5), 0, 20);
        panel.add(nameLabel, nameLabelConstraints);

        //WORKOUT TABLE
        String[] columns = {"Name", "Sets", "Reps", "Unit", "Tier"};
        Object[][] data = {};
        DefaultTableModel table = new DefaultTableModel(data,columns);
        UIUtils.makeWorkoutTable(table, (LinkedList<Exercise>) workout.exerciseList);
        JTable workoutTable = new JTable(table);
        var workoutTableConstraints = tableConstraints;
        JScrollPane scrollPane = new JScrollPane(workoutTable);
        panel.add(scrollPane,workoutTableConstraints);

        int nextIndice = tableConstraints.gridy + tableConstraints.gridwidth + 1;
        tableConstraints.gridy = nextIndice;

        //COMPLETED REPS TABLE
        CheckBoxTableModel checkTableModel = new CheckBoxTableModel(workout.exerciseList);
        UIUtils.makeCheckTable(checkTableModel, workout.exerciseList);
        JTable checkTable = new JTable(checkTableModel);
        var checkTableConstraints = tableConstraints;
        JScrollPane checkPane = new JScrollPane(checkTable);
        panel.add(checkPane, checkTableConstraints);

        nextIndice = tableConstraints.gridy + tableConstraints.gridwidth + 1;

        //CONFIRM BUTTON
        JButton confirmButton = new JButton("Close");
        var confirmButtonConstraints = new GridBagConstraints(0, nextIndice, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0);
        confirmButton.setMinimumSize(new Dimension(40,20));
        confirmButton.setMaximumSize(new Dimension(80,40));
        panel.add(confirmButton, confirmButtonConstraints);
        confirmButton.addActionListener(e ->{
            if(UIUtils.everyCheckIsTrue(checkTableModel)){
                workout.setTimeEnded(LocalTime.now());
                JOptionPane.showMessageDialog(panel, "Great Job! Keep up the good work!\n" +
                                                     "Total Exercise time: " + workout.getWorkoutDuration());
                notifyObservers(workout);
                setVisible(false);
                dispose();
            } else{
                JOptionPane.showMessageDialog(panel,"Ah ah ah, I don't think so, buddy.\n" +
                                                    "Make sure you do the whole circuit!\n" +
                                                    "\n" +
                                                    "'No pain, no gain'\n" +
                                                    "    -Mark Whalberg, probably");
            }
        });

        //Application Window
        setPreferredSize(new Dimension(600, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
