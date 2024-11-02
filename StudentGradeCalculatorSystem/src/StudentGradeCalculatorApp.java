import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class StudentGradeCalculatorApp extends JFrame {
    private StudentGradeCalculator gradeCalculator;
    private JTextField nameField, idField, gradeField;
    private JTextArea outputArea;
    private DefaultTableModel tableModel;

    public StudentGradeCalculatorApp() {
        gradeCalculator = new StudentGradeCalculator();

        setTitle("Student Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top Panel for Form
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Grades (comma-separated):"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(this::addStudent);
        inputPanel.add(addButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Table Panel for Student List
        tableModel = new DefaultTableModel(new Object[]{"Name", "ID", "Average", "Pass/Fail"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel for Outputs and Actions
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton calcClassAvgButton = new JButton("Calculate Class Average");
        calcClassAvgButton.addActionListener(this::calculateClassAverage);
        bottomPanel.add(calcClassAvgButton);

        outputArea = new JTextArea(3, 20);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        bottomPanel.add(outputScrollPane);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addStudent(ActionEvent e) {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String gradesText = gradeField.getText().trim();

        if (name.isEmpty() || id.isEmpty() || gradesText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Double> grades = new ArrayList<>();
        for (String gradeStr : gradesText.split(",")) {
            try {
                grades.add(Double.parseDouble(gradeStr.trim()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Student student = new Student(name, id, grades);
        gradeCalculator.addStudent(student);

        tableModel.addRow(new Object[]{
                student.getName(),
                student.getStudentID(),
                String.format("%.2f", student.calculateAverage()),
                student.hasPassed() ? "Passed" : "Failed"
        });

        nameField.setText("");
        idField.setText("");
        gradeField.setText("");
    }

    private void calculateClassAverage(ActionEvent e) {
        double classAverage = gradeCalculator.calculateClassAverage();
        outputArea.setText("Class Average: " + String.format("%.2f", classAverage));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeCalculatorApp app = new StudentGradeCalculatorApp();
            app.setVisible(true);
        });
    }
}