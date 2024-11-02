import java.util.ArrayList;

public class Student {
    private String name;
    private String studentID;
    private ArrayList<Double> grades;

    public Student(String name, String studentID, ArrayList<Double> grades) {
        this.name = name;
        this.studentID = studentID;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }

    public double calculateAverage() {
        double total = 0;
        for (double grade : grades) {
            total += grade;
        }
        return grades.size() > 0 ? total / grades.size() : 0;
    }

    public boolean hasPassed() {
        return calculateAverage() >= 50;
    }
}