import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            response.sendRedirect(request.getContextPath() + "/admin.jsp?error=An error occurred while deleting the event");
        }
    }
}
