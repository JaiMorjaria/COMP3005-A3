import java.sql.*;

public class A3Q1 {
    private static final String url = "jdbc:postgresql://localhost:5432/students";
    private static final String user = "postgres";
    private static final String password = "YOUR_POSTGRES_PASSWORD";

    public static void getAllStudents(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students;");
            while (resultSet.next()) {
                System.out.print("Student ID: " + resultSet.getInt("student_id") + "\t") ;
                System.out.print("First Name: " + resultSet.getString("first_name") + "\t");
                System.out.print("Last Name: " + resultSet.getString("last_name") + "\t");
                System.out.print("Email: " + resultSet.getString("email") + ",\t");
                System.out.print("Enrollment Date: " + resultSet.getDate("enrollment_date") + "\t");
            }
            statement.close();
            } catch (Exception e) {
                System.out.println("Failed to get all students");
            }
    }

    public static void addStudent(Connection connection, String firstName, String lastName, String email, Date enrollmentDate) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('%s', '%s', '%s', '%s');", firstName, lastName, email, enrollmentDate));
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add " + firstName);
        }
    }

    public static void updateStudentEmail(Connection connection, int studentId, String newEmail) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE students SET email = '%s' WHERE student_id = %d;", newEmail, studentId));
            System.out.println("Email updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update email of student with " + studentId);
        }
    }

    public static void deleteStudent(Connection connection, int studentId) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("DELETE FROM students WHERE student_id = %d;", studentId));
            System.out.println("Student deleted successfully.");
        } catch (Exception e) {
            System.out.println("Failed to delete student with " + studentId);
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            // Test suite
            //Get all students
            System.out.println("All Students:");
            getAllStudents(connection);

            // Add the GOAT
            addStudent(connection, "LeBron", "James", "lebron.james@lakers.com", Date.valueOf("2024-03-18"));
            System.out.println("All Students after adding LeBron:");
            getAllStudents(connection);

            // Update John's email
            updateStudentEmail(connection, 1, "john.doe223@example.com");
            System.out.println("All Students after updating John's email:");
            getAllStudents(connection);

            // Delete LeBron :(
            deleteStudent(connection, 4);
            System.out.println("All Students after deleting LeBron:");
            getAllStudents(connection);
        }
        catch (SQLException e) {
            System.out.println("Connection failure.");
        }
    }
}