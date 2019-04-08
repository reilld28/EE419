import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Random;

import oracle.jdbc.driver.*;

@WebServlet("/CreateRecipe")
public class CreateRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    Statement stmt = null;
    PreparedStatement st = null;
	String JDBCUrl = "jdbc:oracle:thin:@ee417.c7clh2c6565n.eu-west-1.rds.amazonaws.com:1521:EE417";
    String username = "ee_user";
    String password = "ee_pass";
	

  		
 	public void doPost(HttpServletRequest request, HttpServletResponse response)
     	throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
                out.println("<HTML><HEAD><TITLE>Database Servlet</TITLE></HEAD>");
                out.println("<BODY background= https://images.unsplash.com/photo-1507048331197-7d4ac70811cf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1267&q=80.jpeg ><H1 align= center style= color:white; margin-top:20% >Saving Recipe....</H1>");

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
	     		System.out.println("Connection Successful..... creating statement....");
      	     	stmt = con.createStatement();
	     		PreparedStatement st = con.prepareStatement("INSERT INTO DRrecipes (name, category, preparation_time, instructions,ingredient1,ingredient2,ingredient3,ingredient4,ingredient5,quantity1,quantity2,quantity3,quantity4,quantity5,unit1,unit2,unit3,unit4,unit5,recipe_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	     		/*PreparedStatement st = con.prepareStatement("INSERT INTO DRrecipes (name, category, preparation_time, instructions, ingredient1,ingredient2,ingredient3,ingredient4,ingredient5, recipe_id) VALUES (?,?,?,?,?,?,?,?,?,?)");*/
      	     	st.clearParameters();
	     		Integer preparation_time = Integer.parseInt(request.getParameter("preparation_time"));
	     		
	     		Random random = new Random(System.nanoTime());
	     		int id = random.nextInt(1000);
	     		
	     		Integer quantity1 = Integer.parseInt(request.getParameter("quantity1"));
	     		Integer quantity2 = Integer.parseInt(request.getParameter("quantity2"));
	     		Integer quantity3 = Integer.parseInt(request.getParameter("quantity3"));
	     		Integer quantity4 = Integer.parseInt(request.getParameter("quantity4"));
	     		Integer quantity5 = Integer.parseInt(request.getParameter("quantity5"));
	     		
	     		st.setString(1,request.getParameter("name"));
	     		st.setString(2,request.getParameter("category")); 
	     		st.setInt(3,preparation_time); 
	     		st.setString(4,request.getParameter("instructions"));
	     		
	     		

	     		st.setString(5,request.getParameter("ingredient1"));
	     		st.setString(6,request.getParameter("ingredient2"));
	     		st.setString(7,request.getParameter("ingredient3"));
	     		st.setString(8,request.getParameter("ingredient4"));
	     		st.setString(9,request.getParameter("ingredient5"));
	     	
	     		st.setInt(10,quantity1);
	     		st.setInt(11,quantity2);
	     		st.setInt(12,quantity3);
	     		st.setInt(13,quantity4);
	     		st.setInt(14,quantity5);
	     		
	     		
	     		st.setString(15,request.getParameter("unit1"));
	     		st.setString(16,request.getParameter("unit2"));
	     		st.setString(17,request.getParameter("unit3"));
	     		st.setString(18,request.getParameter("unit4"));
	     		st.setString(19,request.getParameter("unit5"));
    		
	     		st.setInt(20,id);

	     		
	     		/*System.out.println("\n The values are: " + quantity1 + quantity2 + quantity3 +quantity4 +quantity5);*/
	     		int row = st.executeUpdate();
	     		if(row==1) {
	     			out.println("<center><H1 style= color:white; margin-top:20% >Recipe Successfully Saved!</H1></center>");
	         	    out.println("<center><a style= color:white; href=Recipe_homepage.html>Click Here to Return Home</a></center>");


	     		}
	     		else {
	     			out.println("Please check your details and try again");
	         	    out.println("<center><a style= color:white; href=Recipe_homepage.html>Click Here to Return Home</a></center>");

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