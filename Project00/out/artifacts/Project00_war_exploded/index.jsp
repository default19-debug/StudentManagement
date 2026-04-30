<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // This perfectly calculates your root URL and sends traffic to the Servlet
    response.sendRedirect(request.getContextPath() + "/students");
%>