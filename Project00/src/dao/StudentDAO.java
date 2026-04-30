package dao;

import model.student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    //connection to database
    private String jdbcURL = "jdbc:postgresql://localhost:5432/practice00_DB";
    private String jdbcUsername = "user";
    private String jdbcPassword = "24112007";

    // 2.manual connection
    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // If it crashes, print the error to the console
        }
        return connection;
    }

    // 3. Read
    public List<student> getAllStudents() {
        List<student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        // try-with-resources block so Java auto closes the connection when done
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            // Loop through every row the database gives us back
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                double gpa = rs.getDouble("gpa");
                String major = rs.getString("major");
                int enrolledYear = rs.getInt("enrolled_year");
                // Turn the database row into a Java Object and add it to our list
                students.add(new student(id, firstName, lastName, gpa, major, enrolledYear));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    // --- 4. READ ONE (Used for filling out the Edit Form) ---
    public student findStudentbyID(int id) {
        student st = null;
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Replace the 1st '?' with the ID we are looking for
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            // If we find a match, pack it into a student object
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                double gpa = rs.getDouble("gpa");
                String major = rs.getString("major");
                int enrolledYear = rs.getInt("enrolled_year");

                st = new student(id, firstName, lastName, gpa, major, enrolledYear);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st;
    }

    // --- 5. CREATE ---
    public void newStudent(student newStudent) {
        String sql = "INSERT INTO students (first_name, last_name, gpa, major, enrolled_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Fill in the 5 blanks (Notice we don't insert ID, PostgreSQL handles that automatically)
            preparedStatement.setString(1, newStudent.getFirstName());
            preparedStatement.setString(2, newStudent.getLastName());
            preparedStatement.setDouble(3, newStudent.getGpa());
            preparedStatement.setString(4, newStudent.getMajor());
            preparedStatement.setInt(5, newStudent.getEnrolledYear());

            // executeUpdate() is used for INSERT, UPDATE, and DELETE
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 6. UPDATE ---
    public void updateStudent(student updatedStudent) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, gpa = ?, major = ?, enrolled_year = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, updatedStudent.getFirstName());
            preparedStatement.setString(2, updatedStudent.getLastName());
            preparedStatement.setDouble(3, updatedStudent.getGpa());
            preparedStatement.setString(4, updatedStudent.getMajor());
            preparedStatement.setInt(5, updatedStudent.getEnrolledYear());
            // Don't forget to set the ID at the end so it knows WHICH student to update
            preparedStatement.setInt(6, updatedStudent.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 7. DELETE ---
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}