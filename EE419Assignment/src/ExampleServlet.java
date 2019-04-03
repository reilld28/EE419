import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;

@WebServlet("/Example")
public class ExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    /*PreparedStatement st = null;*/
	String JDBCUrl = "jdbc:oracle:thin:@ee417.c7clh2c6565n.eu-west-1.rds.amazonaws.com:1521:EE417";
    String username = "ee_user";
    String password = "ee_pass";
  		
 	public void doGet(HttpServletRequest request, HttpServletResponse response)
     	throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
                out.println("<HTML><HEAD><TITLE>Database Servlet</TITLE></HEAD>");
                out.println("<BODY><H1>Database Values</H1>");

		// Now we add our database code!
		try {
             		System.out.println("\nConnecting to the SSD Database......");
             		Class.forName("oracle.jdbc.driver.OracleDriver");
             		con = DriverManager.getConnection(JDBCUrl, username, password);
         	}
         	catch (Exception e) {
             		out.println("An error has occurred during the connection phase! Did you upload your Oracle Drivers?"); 
         	}   

 	 	try {
	     		out.println("Connection Successful..... creating statement....");
      	     		stmt = con.createStatement();
	     		rs = stmt.executeQuery("SELECT * FROM DRusers");
	     		/* PreparedStatement st = con.prepareStatement("INSERT INTO DRusers (accountid,name,email,password) VALUES (?,?,?,?)"); */
	     		

	     		while (rs.next())
                		out.println("<BR>Name=" + rs.getString("name") + " " + rs.getString("email"));
	     				/* st.clearParameters(); */
	     				/* st.setInteger(1,request.getParameter("accountId")); I WANT TO UNIQUE GENERATE*/
	     				/* st.setString(2,request.getParameter("name")); */
	     				/* st.setString(3,request.getParameter("email")); */
	     				/* st.setString(4,request.getParameter("password")); */
	     				/* st.executeUpdate(); */
	     				
	 	}
        catch (Exception e) {
	     		out.println("<BR>An error has occurred during the Statement/ResultSet phase.  Please check the syntax and study the Exception details!");
	    }   
         	
		finally {
	     try {    
	        if (rs != null) rs.close();
		 	if (stmt != null) stmt.close();
		 	if (con != null) con.close();
		 	/* if (st != null) st.close();*/
	     }
	     catch (Exception ex) {
	      	out.println("<BR>An error occurred while closing down connection/statement"); 
         }
        }
		
		out.println("</BODY></HTML>");
        out.close();
 	}
}