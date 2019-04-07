import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
	UserBean user = new UserBean();

	String JDBCUrl = "jdbc:oracle:thin:@ee417.c7clh2c6565n.eu-west-1.rds.amazonaws.com:1521:EE417";
    String username = "ee_user";
    String password = "ee_pass";
  		
 	public void doPost(HttpServletRequest request, HttpServletResponse response)
     	throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
                out.println("<HTML><HEAD><TITLE>Database Servlet</TITLE></HEAD>");
                out.println("<BODY><H1>Checking Login Details....</H1>");

		// Now we add our database code!
		try {
             		/*System.out.println("\nConnecting to the SSD Database......");*/
             		Class.forName("oracle.jdbc.driver.OracleDriver");
             		con = DriverManager.getConnection(JDBCUrl, username, password);
          	        user.setemail(request.getParameter("emailaddress"));
          	        user.setPassword(request.getParameter("emailpassword"));
             		
         	}
         	catch (Exception e) {
             		out.println("An error has occurred during the connection phase! Did you upload your Oracle Drivers?"); 
         	}   

 	 	try {
	     		out.println("Connection Successful..... checking login details....");
      	  
      	        
      	        String email = request.getParameter("emailaddress");
      	        String loginpassword = request.getParameter("emailpassword");
      	        String searchQuery ="select * from DRusers where email='" + email + "' AND password='" + loginpassword+ "'";
      	        
      	        stmt = con.createStatement();  
      	     	rs = stmt.executeQuery(searchQuery);
	     		boolean more = rs.next();
	     		System.out.println("<BR> THis is my select statement" + searchQuery );
	     		
	     		if(!more) {
	      	    out.println("<BR>Error!");  //error page 
	      	    user.setValid(false);
	     			
	     		}
	     		
	     		else if(more) {
	     			String emailaccount = rs.getString("email");
	     			String emailpassword = rs.getString("password");
	     			user.setemail(emailaccount);
	     			user.setPassword(emailpassword);
	     			user.setValid(true);
	     		}
      	     	if (user.isValid())
      	      {
      	 	        
      	           HttpSession session = request.getSession(true);	    
      	           session.setAttribute("currentSessionUser",user); 
      	           out.println("<BR>Successfull login"); //logged-in page      		
      	      }
      	 	        
      	      else {
      	    	out.println("<BR>User details not valid. Please try again");  //error page 
      	 } 
	     				
	 	}
        catch (Exception e) {
	     		out.println("<BR>An error has occurred during the Statement/ResultSet phase.  Please check the syntax and study the Exception details!");
	    }   
         	
		finally {
	     try {    
	        if (rs != null) rs.close();
		 	if (stmt != null) stmt.close();
		 	if (con != null) con.close();
	     }
	     catch (Exception ex) {
	      	out.println("<BR>An error occurred while closing down connection/statement"); 
         }
        }
		
		out.println("</BODY></HTML>");
        out.close();
 	}
}