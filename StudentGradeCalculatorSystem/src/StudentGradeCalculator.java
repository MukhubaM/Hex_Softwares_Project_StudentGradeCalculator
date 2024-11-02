import java.util.ArrayList;

public class StudentGradeCalculator {
    private ArrayList<Student> students;

    public StudentGradeCalculator() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public double calculateClassAverage() {
        double total = 0;
        int count = 0;
        for (Student student : students) {
            total += student.calculateAverage();
            count++;
        }
        return count > 0 ? total / count : 0;
    }
}