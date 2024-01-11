<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Events4U.</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
    <div class="collapse navbar-collapse justify-content-end p-md-1" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                        <a class="nav-link" href="adminlogin.html">Logout</a>
                </li>
            </ul>
        </div>
        </nav>

    <div class="container">
        <h1 class="display-3 text-center m-3">
            Admin Panel
        </h1>

        <%
      	
        	String userName = request.getParameter("userName");
            String password = request.getParameter("password");

            if (userName == null && password == null || userName.equals("admin") && password.equals("admin")) {
            	  session = request.getSession(true);
                  session.setAttribute("userName", userName);
                  session.setAttribute("password", password);
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventsystem", "root", "");
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
                    ResultSet resultSet = statement.executeQuery();
                
        %>

         <form action="ConfirmBookingServlet" method="post">
            <table class="table">
                <thead>
                    <tr>
                        <th>Event Name</th>
                        <th>Event Date</th>
                        <th>Event Time</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        while (resultSet.next()) {
                    %>
                             <tr>
                                <td><%= resultSet.getString("event_name") %></td>
                                <td><%= resultSet.getString("event_date") %></td>
                                <td><%= resultSet.getString("event_time") %></td>
                                <td>
                                    <a href="editEvent.jsp?id=<%= resultSet.getInt("eventId") %>">Edit</a>
                                    |
                                    <a href="deleteEvent.jsp?id=<%= resultSet.getInt("eventId") %>">Delete</a>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </form>
    </div>

    <%
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    response.getWriter().println("An error occurred while accessing the database.");
                }
            } else {
                // Handle invalid login
                response.setContentType("text/html");
                PrintWriter pw = response.getWriter();
                pw.print("<script>");
                pw.print("alert('Username or Password is wrong);");
                pw.print("</script>");
                // Consider redirecting to the login page instead of including it
                response.sendRedirect(request.getContextPath() + "/adminlogin.html");
            }
        %>
</body>
</html>
