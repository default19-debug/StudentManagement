<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.student" %>
<html>
<head>
    <title>Student Management Dashboard</title>
    <style>
        /* 1990s Simple Styling */
        body { font-family: sans-serif; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #333; padding: 10px; text-align: left; }
        th { background-color: #eee; }
        .btn-new { display: inline-block; padding: 8px 12px; background: #007bff; color: white; text-decoration: none; font-weight: bold; }
        .action-link { text-decoration: none; color: blue; }
        .action-link.delete { color: red; }
    </style>
</head>
<body>

<h2>Student Directory</h2>

<!-- The CREATE link routing to ?action=new -->
<a href="students?action=new" class="btn-new">+ Add New Student</a>

<table>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>GPA</th>
        <th>Major</th>
        <th>Enrolled Year</th>
        <th>Actions</th>
    </tr>

    <%
        // 1. Unpack the data sent by the Servlet
        List<student> students = (List<student>) request.getAttribute("listStudents");

        // 2. Loop through the data if it exists
        if (students != null && !students.isEmpty()) {
            for (student s : students) {
    %>
    <tr>
        <td><%= s.getId() %></td>
        <td><%= s.getFirstName() %></td>
        <td><%= s.getLastName() %></td>
        <td><%= s.getGpa() %></td>
        <td><%= s.getMajor() %></td>
        <td><%= s.getEnrolledYear() %></td>
        <td>
            <!-- The UPDATE and DELETE links routing to ?action=...&id=... -->
            <a href="students?action=edit&id=<%= s.getId() %>" class="action-link">Edit</a> |
            <a href="students?action=delete&id=<%= s.getId() %>" class="action-link delete" onclick="return confirm('Are you sure you want to delete <%= s.getFirstName() %>?');">Delete</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <!-- Spans all 7 columns if the database is empty -->
        <td colspan="7" style="text-align: center;">No students found in the database.</td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>