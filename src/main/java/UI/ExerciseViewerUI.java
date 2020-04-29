package UI;


import domain.Exercise;
import domain.WorkoutGenerator;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

public class ExerciseViewerUI extends JFrame implements ActionListener {

    public ExerciseViewerUI(Exercise ex){

        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        panel.setName("Exercise Viewer Interface");
        setContentPane(panel);

        String[] rowNames = {"Name","Sets", "Reps", "Unit"};
        String[] rowFillers = {ex.name, Integer.toString(ex.sets), Integer.toString(ex.reps), ex.unit, ex.description};
        for(int i=0; i<rowNames.length; i++){
            //LABEL MAKER
            JLabel nameLabel = new JLabel(rowNames[i]);
            var nameLabelConstraints = new GridBagConstraints(0, i,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0);
            panel.add(nameLabel,nameLabelConstraints);

            //INPUT MAKER
            JLabel txtLabel = new JLabel(rowFillers[i]);
            var txtFieldConstraints = new GridBagConstraints(1, i, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
            panel.add(txtLabel, txtFieldConstraints);
        }
        int nextIndice = rowNames.length;

        JLabel descriptionLabel = new JLabel("Description");
        var descriptionLabelConstraints = new GridBagConstraints(0, nextIndice,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(descriptionLabel,descriptionLabelConstraints);

        JTextArea textArea = new JTextArea(rowFillers[4]);
        textArea.setColumns(10);
        textArea.setRows(5);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        var areaScrollPaneConstraints = new GridBagConstraints(1, nextIndice,1,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(areaScrollPane,areaScrollPaneConstraints);

        nextIndice++;

        //MUSCLEGROUPS LABEL
        JLabel muscleGroupsLabel = new JLabel("Muscle Groups");
        var muscleGroupsLabelConstraints = new GridBagConstraints(0, nextIndice, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(muscleGroupsLabel, muscleGroupsLabelConstraints);

        //MUSCLEGROUPS LIST
        JList<String> muscleGroupsList = new JList<>();
        muscleGroupsList.setListData(ex.getMuscleGroupsArray());
        var muscleGroupsListConstraints = new GridBagConstraints(1, nextIndice, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(muscleGroupsList, muscleGroupsListConstraints);

        //CLOSE BUTTON
        JButton confirmButton = new JButton("Close");
        var confirmButtonConstraints = new GridBagConstraints(0, nextIndice + 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
        panel.add(confirmButton, confirmButtonConstraints);
        confirmButton.addActionListener(e ->{
            setVisible(false);
            dispose();
        });

        //Application Window
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
