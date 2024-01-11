<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Event - Events4U.</title>
</head>
<body>
    <%
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventsystem", "root", "");
            PreparedStatement statement = connection.prepareStatement("UPDATE events SET event_name = ?, event_date = ?, event_time = ? WHERE eventId = ?");
            statement.setString(1, eventName);
            statement.setString(2, eventDate);
            statement.setString(3, eventTime);
            statement.setInt(4, eventId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
            	String contextPath = request.getContextPath();
                String redirectURL = contextPath + "/admin.jsp?success=Event updated successfully";
                response.sendRedirect(redirectURL);
            } else {
                String contextPath = request.getContextPath();
                String redirectURL = contextPath + "/admin.jsp?error=Failed to update event";
                response.sendRedirect(redirectURL);
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("admin.jsp?error=An error occurred while updating the event");
        }
    %>
</body>
</html>
