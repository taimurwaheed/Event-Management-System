import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC database URL, username, and password
    String JDBC_URL = "jdbc:mysql://localhost:3306/eventsystem";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        
        // Initialize database connection and prepared statement
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the JDBC driver
            try (
                Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?"
                )
            ) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                // Execute the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // User exists, login successful
                        HttpSession session = request.getSession(true);
                        String userName = resultSet.getString("name"); // Assuming the name column in your database is "name"
                        String Id = resultSet.getString("Id");
                        
                        session.setAttribute("Id", Id);
                        session.setAttribute("email", email);
                        session.setAttribute("userName", userName);

                        // Set session timeout to 10 minutes (adjust as needed)
                        session.setMaxInactiveInterval(10 * 60);
                        
                        response.setContentType("text/html");
						/*
						 * PrintWriter pw = response.getWriter();
						 * pw.print("<h1 style='color:green;'>Logged In Successfully, Welcome " +
						 * userName + "</h1>");
						 */
                        RequestDispatcher rd = request.getRequestDispatcher("/Home.html");
                        rd.include(request, response);
                    } else {
                        // User does not exist, login failed
                        response.setContentType("text/html");
                        PrintWriter pw = response.getWriter();
                        pw.print("<h3 style='color:red;'>Invalid Login Credentials<h3>");
                        RequestDispatcher rd = request.getRequestDispatcher("/Login.html");
                        rd.include(request, response);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle ClassNotFoundException and SQLException
            e.printStackTrace();
            response.getWriter().println("Error: Could not process the request. Please try again later.");
        }
    }
}
