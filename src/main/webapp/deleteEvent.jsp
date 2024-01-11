<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Event - Events4U.</title>
</head>
<body>
    <%
        try {
            // Get event ID from request parameter
            int eventId = Integer.parseInt(request.getParameter("id"));

            // JDBC Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventsystem", "root", "");

            // Prepared statement to delete event by ID
            PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE eventId = ?");
            statement.setInt(1, eventId);

            // Execute the delete operation
            int affectedRows = statement.executeUpdate();

            // Check the result and redirect accordingly
             if (affectedRows > 0) {
	        String contextPath = request.getContextPath();
	        String redirectURL = contextPath + "/admin.jsp?success=Event deleted successfully";
	        response.sendRedirect(redirectURL);
		    } else {
		        String contextPath = request.getContextPath();
		        String redirectURL = contextPath + "/admin.jsp?error=Failed to delete event";
		        response.sendRedirect(redirectURL);
		    }

            // Close resources
            statement.close();
            connection.close();
        } catch (NumberFormatException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("Event_Management_System/admin.jsp?error=An error occurred while deleting the event");
        }
    %>
</body>
</html>
