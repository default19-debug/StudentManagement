package controller;

import dao.StudentDAO;
import model.student;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    // This runs once when Tomcat boots up the Servlet
    public void init() {
        studentDAO = new StudentDAO();
    }

    // --- 1. THE ROUTERS ---

    // Handles GET requests (Viewing data, clicking links, loading forms)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Look at the URL to see what the user wants to do
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // If no action is provided, default to listing students
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    // Handles POST requests (Submitting HTML forms)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "insert":
                    insertStudent(request, response);
                    break;
                case "update":
                    updateStudent(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    // --- 2. THE HELPER METHODS (The actual logic) ---

    // READ: Get all students and send them to the dashboard
    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<student> listStudents = studentDAO.getAllStudents();
        request.setAttribute("listStudents", listStudents);
        request.getRequestDispatcher("/student-dashboard.jsp").forward(request, response);
    }

    // READ: Just show the blank HTML form to create a student
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    // READ: Get one specific student and send them to the form to be edited
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        student existingStudent = studentDAO.findStudentbyID(id);
        request.setAttribute("student", existingStudent);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    // CREATE: Take form data, make a student, save to DB, and refresh page
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        String major = request.getParameter("major");
        int enrolledYear = Integer.parseInt(request.getParameter("enrolledYear"));

        student newStudent = new student(firstName, lastName, gpa, major, enrolledYear);
        studentDAO.newStudent(newStudent);

        // Redirect back to the main list after saving
        response.sendRedirect("students");
    }

    // UPDATE: Take edited form data, update DB, and refresh page
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        double gpa = Double.parseDouble(request.getParameter("gpa"));
        String major = request.getParameter("major");
        int enrolledYear = Integer.parseInt(request.getParameter("enrolledYear"));

        student updatedStudent = new student(id, firstName, lastName, gpa, major, enrolledYear);
        studentDAO.updateStudent(updatedStudent);

        response.sendRedirect("students");
    }

    // DELETE: Grab the ID from the URL, delete from DB, and refresh page
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);

        response.sendRedirect("students");
    }
}