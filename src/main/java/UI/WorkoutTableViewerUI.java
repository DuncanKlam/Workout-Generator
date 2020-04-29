package UI;

import utils.DatabaseUtils;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorkoutTableViewerUI extends JFrame implements ActionListener {

    public  WorkoutTableViewerUI(){
        try {
            this.databaseUtils = new DatabaseUtils();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public interface Observer {
        void update(String name);
    }

    private final ArrayList<WorkoutTableViewerUI.Observer> observers = new ArrayList<>();

    private void notifyObservers(String name) {
        observers.forEach(observer -> observer.update(name));
    }

    public void addObserver(WorkoutTableViewerUI.Observer observer) {
        observers.add(observer);
    }

    public DatabaseUtils databaseUtils;

    public void showWorkoutTable(){
        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        JPanel panel = new JPanel();

        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        panel.setName("Exercise Viewer Interface");
        setContentPane(panel);

        //WORKOUT TABLE
        String[] columns = {"Name","Intensity","Duration"};
        Object[][] data = {};
        DefaultTableModel tableData = new DefaultTableModel(data,columns);
        JTable workoutTable = new JTable(tableData);
        var workoutTableConstraints = new GridBagConstraints(0, 0, 3, 6, 2, 2, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 400);
        JScrollPane JScrollPane = new JScrollPane(workoutTable);
        panel.add(JScrollPane,workoutTableConstraints);

        //DELETE WORKOUT BUTTON
        JButton deleteButton = new JButton("Delete Workout");
        var deleteButtonConstraints = new GridBagConstraints(0, 7, 1, 1, 1, 1, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(10, 20, 5, 20), 0, 0);
        panel.add(deleteButton, deleteButtonConstraints);
        deleteButton.addActionListener(e ->{
            int selectedRow = workoutTable.getSelectedRow();
            if(selectedRow == -1){
                JOptionPane.showMessageDialog(panel, "Choose exercise in list to delete");
            } else {
                String name = (String) workoutTable.getValueAt(selectedRow, 0);
                name += "Z" + workoutTable.getValueAt(selectedRow, 2);
                notifyObservers(name);
                UIUtils.updateWorkoutTableData(tableData, databaseUtils.readWorkoutTable());
            }
        });

        //CLOSE BUTTON
        JButton confirmButton = new JButton("Close");
        var confirmButtonConstraints = new GridBagConstraints(0, 8, 1, 1, 1, 1, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 10, 20), 0, 0);
        panel.add(confirmButton, confirmButtonConstraints);
        confirmButton.addActionListener(e ->{
            setVisible(false);
            dispose();
        });

        UIUtils.updateWorkoutTableData(tableData, databaseUtils.readWorkoutTable());

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
