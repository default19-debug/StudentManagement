<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.student" %>
<%
    // 1. Check if we have a student object (this means we clicked "Edit")
    student existingStudent = (student) request.getAttribute("student");


    String actionUrl = (existingStudent != null) ? "?action=update" : "students?action=insert";
    String formTitle = (existingStudent != null) ? "Edit Student Profile" : "Add New Student";
    String buttonText = (existingStudent != null) ? "Update Student" : "Save New Student";
%>
<html>
<head>
    <title><%= formTitle %></title>
    <style>
        /* Keeping it purely functional */
        body { font-family: sans-serif; padding: 20px; }
        .form-container { width: 400px; background: #f9f9f9; padding: 20px; border: 2px solid #ccc; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; }
        input[type="text"], input[type="number"] { width: 100%; padding: 8px; box-sizing: border-box; }
        .btn-submit { background: #007bff; color: white; border: none; padding: 10px 15px; font-weight: bold; cursor: pointer; }
        .btn-cancel { text-decoration: none; color: #d9534f; margin-left: 15px; font-weight: bold; }
    </style>
</head>
<body>

<h2><%= formTitle %></h2>

<div class="form-container">
    <!-- The form submits via POST to the URL we defined at the top -->
    <form action="<%= actionUrl %>" method="POST">

        <%-- CRITICAL: If we are updating, we MUST send the ID back to the Servlet,
             but we don't want the user typing in a fake ID, so we hide it. --%>
        <% if (existingStudent != null) { %>
        <input type="hidden" name="id" value="<%= existingStudent.getId() %>" />
        <% } %>

        <div class="form-group">
            <label>First Name:</label>
            <input type="text" name="firstName" required
                   value="<%= (existingStudent != null) ? existingStudent.getFirstName() : "" %>" />
        </div>

        <div class="form-group">
            <label>Last Name:</label>
            <input type="text" name="lastName" required
                   value="<%= (existingStudent != null) ? existingStudent.getLastName() : "" %>" />
        </div>

        <div class="form-group">
            <label>GPA:</label>
            <input type="text" name="gpa" required
                   value="<%= (existingStudent != null) ? existingStudent.getGpa() : "" %>" />
        </div>

        <div class="form-group">
            <label>Major:</label>
            <input type="text" name="major" required
                   value="<%= (existingStudent != null) ? existingStudent.getMajor() : "" %>" />
        </div>

        <div class="form-group">
            <label>Enrolled Year (e.g., 2026):</label>
            <input type="number" name="enrolledYear" required
                   value="<%= (existingStudent != null) ? existingStudent.getEnrolledYear() : "" %>" />
        </div>

        <div class="form-group">
            <button type="submit" class="btn-submit"><%= buttonText %></button>
            <a href="students" class="btn-cancel">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>