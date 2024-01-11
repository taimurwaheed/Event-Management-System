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

@WebServlet("/AdminDisplayServlet")
public class AdminDisplayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC database URL, username, and password
//    String JDBC_URL = "jdbc:mysql://localhost:3306/eventsystem";
//    String JDBC_USER = "root";
//    String JDBC_PASSWORD = "";

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        if (userName.equals("admin") && password.equals("admin")) {
        	RequestDispatcher rd = request.getRequestDispatcher("/admin.html");
        	rd.forward(request, response);
        	}
        	else {
        	response.setContentType("text/html");
        	PrintWriter pw = response.getWriter();
        	pw.print("<h3 style='color:red;'>Invalid UserName and Password<h3>");
        	RequestDispatcher rd = request.getRequestDispatcher("/adminlogin.html");
        	rd.include(request, response);
        	}
        
        try {
        	  //  Block of code to try
        	}
        	catch(Exception e) {
        	  //  Block of code to handle errors
        	}

    }
}
