package UI;


import domain.Exercise;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;


public class ExerciseMakerUI extends JFrame implements ActionListener {

    public ExerciseMakerUI(){ }

    public interface Observer {
        void update(Exercise newExercise);
    }

    private final ArrayList<Observer> observers = new ArrayList<>();

    private void notifyObservers(Exercise newExercise) {
        observers.forEach(observer -> observer.update(newExercise));
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void makeExercise(){

        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        panel.setName("Exercise Viewer Interface");
        setContentPane(panel);


        String[] rowNames = {"Name","Sets", "Reps", "Unit","Description"};
        for(int i=0; i<rowNames.length; i++){
            //LABEL MAKER
            JLabel nameLabel = new JLabel(rowNames[i]);
            var nameLabelConstraints = new GridBagConstraints(0, i,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0);
            panel.add(nameLabel,nameLabelConstraints);

            //INPUT MAKER
            JTextField txtInput = new JTextField("");
            var txtInputConstraints = new GridBagConstraints(1, i, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
            panel.add(txtInput, txtInputConstraints);
        }
        int nextIndice = rowNames.length + 1;

        //MUSCLEGROUPS LABEL
        JLabel muscleGroupsLabel = new JLabel("Muscle Groups");
        var muscleGroupsLabelConstraints = new GridBagConstraints(0, nextIndice, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(muscleGroupsLabel, muscleGroupsLabelConstraints);

        String[] muscleGroups = {"Chest","Upper Back","Lower Back","Abs","Bicep","Tricep","Shoulder","Calves","Quads","Glutes"};
        JCheckBox[] checkBoxes = new JCheckBox[muscleGroups.length];
        for(int i=0; i<muscleGroups.length; i++) {
            JCheckBox checkBox = new JCheckBox(muscleGroups[i]);
            var checkBoxConstraints = new GridBagConstraints(1, nextIndice + i, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
            panel.add(checkBox, checkBoxConstraints);
            checkBoxes[i] = checkBox;
        }

        //CONFIRM BUTTON
        JButton confirmButton = new JButton("Confirm");
        var confirmButtonConstraints = new GridBagConstraints(0, nextIndice + (muscleGroups.length/2), 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(confirmButton, confirmButtonConstraints);
        confirmButton.addActionListener(e ->{
            String name = UIUtils.getTextFromComponent(panel,1);
            int sets = UIUtils.getIntFromComponent(panel,3);
            int reps = UIUtils.getIntFromComponent(panel,5);
            String unit = UIUtils.getTextFromComponent(panel,7);
            String description = UIUtils.getTextFromComponent(panel,9);
            LinkedList<String> muscles = UIUtils.getMusclesList(checkBoxes);
            notifyObservers(new Exercise(name, sets, reps, unit, muscles, description));
            setVisible(false);
            dispose();
        });

        //Application Window
        setPreferredSize(new Dimension(400, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
