<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Event - Events4U.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	    <%
        int eventId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventsystem", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE eventId = ?");
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String eventName = resultSet.getString("event_name");
                String eventDate = resultSet.getString("event_date");
                String eventTime = resultSet.getString("event_time");

                statement.close();
                resultSet.close();

                out.println("<div class='container mt-4'>");
                out.println("<h1 class='mb-4'>Edit Event</h1>");
                out.println("<form action='updateEvent.jsp' method='post' class='needs-validation'>");
                out.println("<input type='hidden' name='eventId' value='" + eventId + "'>");
                out.println("<div class='row g-3'>");
                out.println("<div class='col-md-6'>");
                out.println("<label for='eventName' class='form-label'>Event Name:</label>");
                out.println("<input type='text' name='eventName' id='eventName' value='" + eventName + "' class='form-control' required>");
                out.println("</div>");
                out.println("<div class='col-md-6'>");
                out.println("<label for='eventDate' class='form-label'>Event Date:</label>");
                out.println("<input type='date' name='eventDate' id='eventDate' value='" + eventDate + "' class='form-control' required>");
                out.println("</div>");
                out.println("<div class='col-md-6'>");
                out.println("<label for='eventTime' class='form-label'>Event Time:</label>");
                out.println("<input type='time' name='eventTime' id='eventTime' value='" + eventTime + "' class='form-control' required>");
                out.println("</div>");
                out.println("<div class='col-12'>");
                out.println("<button type='submit' class='btn btn-primary'>Save Changes</button>");
                out.println("</div>");
                out.println("</div>"); // Close row
                out.println("</form>");
                out.println("</div>"); // Close container
            } else {
                out.println("<p>Event not found.</p>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>An error occurred while retrieving event details.</p>");
            e.printStackTrace();
        }
    %>
</body>
</html>
