import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC database URL, username, and password
    String JDBC_URL = "jdbc:mysql://localhost:3306/insert";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Perform database insertion
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Use the correct database URL
            String JDBC_URL = "jdbc:mysql://localhost:3306/eventsystem";
            String DBUserName = "root";
            String DBPassword = "";

            Connection con = DriverManager.getConnection(JDBC_URL, DBUserName, DBPassword);

            if (con != null) {
                // SQL query to insert user data into the database
                String insertQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                int rowInserted = preparedStatement.executeUpdate();
                if (rowInserted > 0) {
                    response.setContentType("text/html");
                    PrintWriter pw = response.getWriter();
                    pw.print("<h1 style='color:yellow;'>User Registered Successfully<h3>");
                    RequestDispatcher rd = request.getRequestDispatcher("/Register.html");
                    rd.include(request, response);
                } else {
                    response.setContentType("text/html");
                    PrintWriter pw = response.getWriter();
                    pw.print("<h1 style='color:red;'>Invalid Registration<h3>");
                    RequestDispatcher rd = request.getRequestDispatcher("/Register.html");
                    rd.include(request, response);
                }

                preparedStatement.close();
                con.close();
            } else {
                response.getWriter().println("Connection not established");
            }
        } catch (Exception e) {
            PrintWriter pw = response.getWriter();
            pw.print("Exception" + e);
        }

        // After retrieving form data and before forwarding to the JSP
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // Set attributes to pass data to JSP
        request.setAttribute("Name", name);
        request.setAttribute("Email", email);
        request.setAttribute("Password", password);

        // Forward the request to the JSP page
        request.getRequestDispatcher("/Login.html").forward(request, response);
    }

}
