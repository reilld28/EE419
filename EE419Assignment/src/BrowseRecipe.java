import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;

@WebServlet("/Browse")
public class BrowseRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

	String JDBCUrl = "jdbc:oracle:thin:@ee417.c7clh2c6565n.eu-west-1.rds.amazonaws.com:1521:EE417";
    String username = "ee_user";
    String password = "ee_pass";
  		
 	public void doGet(HttpServletRequest request, HttpServletResponse response)
     	throws ServletException, IOException {
        	response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
                out.println("<HTML><HEAD><TITLE>Database Servlet</TITLE></HEAD>");
                out.println("<BODY><H1>Recipes in Database</H1>");

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
	     		out.println("Connection Successful..... checking recipe details....");
      	  
      	        
      	        String decision = request.getParameter("action");
	     		
	     		out.println("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=100%>"
	     	              +"<tr><th>Name</th><th>Category</th><th>Instructions</th><th>Ingredients</th></tr>");
	     		
      	        
      	        if("All Recipes".equals(decision)) {
      	        
      	        	String searchQuery ="select * from DRrecipes";
          	        stmt = con.createStatement();  
          	     	rs = stmt.executeQuery(searchQuery);
          	     	while (rs.next()) {
    		
          	     		out.println("<tr><td><center>"+rs.getString("name")+"</center></td>"+ "<td><center>"+rs.getString("category")+"</center></td>"+ "<td><center>" + rs.getString("instructions") + "</center></td>"
          	     				+ "<td><center>" + rs.getString("quantity1") + " " + rs.getString("unit1")+ " " + rs.getString("ingredient1")
          	     				+ "<br>" + rs.getString("quantity2") + " " + rs.getString("unit2")+ " " + rs.getString("ingredient2") 
          	     				+ "<br>" + rs.getString("quantity3") + " " + rs.getString("unit3")+ " " + rs.getString("ingredient3")
          	     				+ "<br>" + rs.getString("quantity4") + " " + rs.getString("unit4")+ " "  + rs.getString("ingredient4") 
          	     				+ "<br>" + rs.getString("quantity5") + " " + rs.getString("unit5")+ " " + rs.getString("ingredient5") + "</center></td></tr>");
          	     	}
      	        }
      	        
      	        else if ("Search".equals(decision)){
      	        	String keyword = request.getParameter("keyword");
      	        	String keyword1 = keyword.toUpperCase();
      	        	System.out.println(keyword1);
      	        	String searchQuery ="select * from DRrecipes WHERE  UPPER(name) LIKE '%" +  keyword1 + "%' OR UPPER(ingredient1) LIKE '%"+ keyword1 
      	        			+ "%' OR UPPER(ingredient2) LIKE '%" + keyword1 + "%' OR UPPER(ingredient3) LIKE '%" + keyword1 + "%' OR UPPER(ingredient4) LIKE '%"
      	        			+ keyword1 + "%' OR UPPER(ingredient5) LIKE '%" + keyword1 + "%'";
      	        	System.out.println(searchQuery);
          	        stmt = con.createStatement();  
          	     	rs = stmt.executeQuery(searchQuery);
          	     	while (rs.next()) {
          	    		
          	     		out.println("<tr><td><center>"+rs.getString("name")+"</center></td>"+ "<td><center>"+rs.getString("category")+"</center></td>"+ "<td><center>" + rs.getString("instructions") + "</center></td>"
          	     				+ "<td><center>" + rs.getString("quantity1") + " " + rs.getString("unit1")+ " " + rs.getString("ingredient1")
          	     				+ "<br>" + rs.getString("quantity2") + " " + rs.getString("unit2")+ " " + rs.getString("ingredient2") 
          	     				+ "<br>" + rs.getString("quantity3") + " " + rs.getString("unit3")+ " " + rs.getString("ingredient3")
          	     				+ "<br>" + rs.getString("quantity4") + " " + rs.getString("unit4")+ " "  + rs.getString("ingredient4") 
          	     				+ "<br>" + rs.getString("quantity5") + " " + rs.getString("unit5")+ " " + rs.getString("ingredient5") + "</center></td></tr>");
          	     	}
          	     	
      	        }
      	        
      	        else if ("Category".equals(decision)) {
      	        	System.out.println("I'm here");
      	        	
      	        	String cat = request.getParameter("Category");
      	        	String cat1 = cat.toUpperCase();
      	        	System.out.println(cat);
      	        	String searchQuery ="select * from DRrecipes WHERE  UPPER(Category) LIKE '%" +  cat1 + "%'";
      	        	System.out.println(searchQuery);
          	        stmt = con.createStatement();  
          	     	rs = stmt.executeQuery(searchQuery);
          	     	while (rs.next()) {
          	    		
          	     		out.println("<tr><td><center>"+rs.getString("name")+"</center></td>"+ "<td><center>"+rs.getString("category")+"</center></td>"+ "<td><center>" + rs.getString("instructions") + "</center></td>"
          	     				+ "<td><center>" + rs.getString("quantity1") + " " + rs.getString("unit1")+ " " + rs.getString("ingredient1")
          	     				+ "<br>" + rs.getString("quantity2") + " " + rs.getString("unit2")+ " " + rs.getString("ingredient2") 
          	     				+ "<br>" + rs.getString("quantity3") + " " + rs.getString("unit3")+ " " + rs.getString("ingredient3")
          	     				+ "<br>" + rs.getString("quantity4") + " " + rs.getString("unit4")+ " "  + rs.getString("ingredient4") 
          	     				+ "<br>" + rs.getString("quantity5") + " " + rs.getString("unit5")+ " " + rs.getString("ingredient5") + "</center></td></tr>");
          	     	}
      	        	
      	        }
      	      out.println("</table>");
      	        
      	        	     		
	     				
	 	}
 	 	catch (SQLException e) {
 		     System.out.println("\nAn error has occurred during the Statement/ResultSet phase.  Please check the syntax and study the Exception details!");
 	             while (e != null) {
 		         System.out.println(e.getMessage());
 	                 e = e.getNextException();
 		     }
 	             System.exit(0);
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