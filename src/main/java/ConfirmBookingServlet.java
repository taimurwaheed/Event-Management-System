import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC database URL, username, and password
    String JDBC_URL = "jdbc:mysql://localhost:3306/eventsystem";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "";
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve session attributes
        HttpSession session = request.getSession(true); // Ensure the session is created if it doesn't exist
        if (session != null) {

                // Retrieve form data
        		String eventName = request.getParameter("eventName");
                String eventDate = request.getParameter("eventDate");
                String eventTime = request.getParameter("eventTime");
                
                String Id=(String)session.getAttribute("Id");


                // Initialize database connection and prepared statement
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver"); // Load the JDBC driver
                   
                        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                        PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO events (event_name, event_date, event_time, user_id) VALUES (?, ?, ?, ?)"
                        );
                     {
                    	preparedStatement.setString(1, eventName);
                        preparedStatement.setString(2, eventDate);
                        preparedStatement.setString(3, eventTime);
                        preparedStatement.setString(4, Id);

                        // Execute the query
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            // Booking successful
                            response.setContentType("text/html");
                            PrintWriter pw = response.getWriter();
                            // pw.print("<h1 style='color:green;'>Booking Confirmed Successfully</h1>");
							/*
							 * pw.print("<p>Event Date: " + eventDate + "</p>"); pw.print("<p>Event Time: "
							 * + eventTime + "</p>");
							 */
                            pw.print("<script>");
                            pw.print("alert('Booking Confirmed Successfully\\nEvent Name: "+ eventName  + "\\nEvent Time: " + eventDate + "\\nEvent Time: " + eventTime + "');");
                            pw.print("</script>");

                            // Redirect to home.html with a success message
                            RequestDispatcher rd = request.getRequestDispatcher("/Home.html");
                            rd.include(request, response);
                        } else {
                            // Booking failed
                            response.setContentType("text/html");
                            PrintWriter pw = response.getWriter();
                            pw.print("<h1 style='color:red;'>Booking Failed</h1>");
                            pw.print("<p>Sorry, there was an issue confirming your booking. Please try again.</p>");
                            RequestDispatcher rd = request.getRequestDispatcher("/Music.html");
                            rd.include(request, response);
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    // Print the detailed error message
                    e.printStackTrace();

                    // Respond with a more detailed error message to the client
                    response.setContentType("text/html");
                    PrintWriter pw = response.getWriter();
                    pw.print("<h1 style='color:red;'>Please login to Book Festivals </h1>");
                    RequestDispatcher rd = request.getRequestDispatcher("/Login.html");
                    rd.include(request, response);
					/*
					 * } else { // Handle the case when eventId is null
					 * response.setContentType("text/html"); PrintWriter pw = response.getWriter();
					 * pw.
					 * print("<h1 style='color:red;'>Error: Event information not found. Please try again.</h1>"
					 * ); RequestDispatcher rd = request.getRequestDispatcher("/Music.html");
					 * rd.include(request, response);
					 */
            }
        } else {
            // Session attributes are not available, redirect to login
            response.sendRedirect("Login.html");
        }
    }
}
