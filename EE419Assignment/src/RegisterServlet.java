import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.util.Random;



@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    Statement stmt = null;
    PreparedStatement st = null;
	String JDBCUrl = "jdbc:oracle:thin:@ee417.c7clh2c6565n.eu-west-1.rds.amazonaws.com:1521:EE417";
    String username = "ee_user";
    String password = "ee_pass";
	UserBean user = new UserBean();

  	
 	public void doPost(HttpServletRequest request, HttpServletResponse response)
     	throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
                out.println("<HTML><HEAD><TITLE>Database Servlet</TITLE></HEAD>");
                out.println("<BODY background= https://images.unsplash.com/photo-1507048331197-7d4ac70811cf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1267&q=80.jpeg><H1 align= center style= color:white; margin-top:20% >Registering New User....</H1>");

		// Now we add our database code!
		try {
             		System.out.println("\nConnecting to the SSD Database......");
             		Class.forName("oracle.jdbc.driver.OracleDriver");
             		con = DriverManager.getConnection(JDBCUrl, username, password);
             		user.setemail(request.getParameter("email"));
          	        user.setPassword(request.getParameter("password"));
          	        user.setname(request.getParameter("name"));
             
         	}
         	catch (Exception e) {
             		System.out.println("An error has occurred during the connection phase! Did you upload your Oracle Drivers?"); 
         	}   

 	 	try {
	     		System.out.println("Connection Successful..... creating statement....");
      	     	stmt = con.createStatement();
	     		PreparedStatement st = con.prepareStatement("INSERT INTO DRusers (accountid,name,email,password) VALUES (?,?,?,?)");
      
	     		Random random = new Random(System.nanoTime());
	     		int id = random.nextInt(1000);
	     		
	     		st.clearParameters();
	     		st.setInt(1,id); 
	     		st.setString(2,request.getParameter("name"));
	     		st.setString(3,request.getParameter("email")); 
	     		st.setString(4,request.getParameter("password"));
	     		
	     		String name = request.getParameter("name");
	     		String email = request.getParameter("email");
	     		String password = request.getParameter("password");
	     		
	     		System.out.println("\n The name is " + name + " the email is " + email + "the password is " + password);
	     		int row = st.executeUpdate();
	     		if(row==1) {
	     			out.println("<H1 align= center style= color:white; margin-top:20%>Registration Successful! Welcome " + user.getname() + "!</H1>");
	     		}
	     		else {
	     			out.println("<H1 align= center style= color:white; margin-top:20%>Please check your details and try again</H1>");
	     		}
	     				
	 	}
        catch (Exception e) {
	     		out.println("<BR>An error has occurred during the Statement/ResultSet phase.  Please check the syntax and study the Exception details!");
	    }   
         	
		finally {
	     try {    
		 	if (stmt != null) stmt.close();
		 	if (con != null) con.close();
		 	if (st != null) st.close();
	     }
	     catch (Exception ex) {
	      	out.println("<BR>An error occurred while closing down connection/statement"); 
         }
        }
		
		out.println("</BODY></HTML>");
        out.close();
 	}
}