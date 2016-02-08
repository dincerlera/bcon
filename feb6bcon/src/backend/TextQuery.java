package backend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/textquery")
public class TextQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private BconDAO bconDAO = new BconDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchtext = request.getParameter("searchtext").trim();
		String token = request.getParameter("token");
		
		Bcon bcon = new Bcon();	
		bcon.setSearchtext(searchtext);
		bcon.setToken(token);
		
		String responseMsg = null;
		
		try {
			responseMsg = bconDAO.textquery(bcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append(responseMsg);
	}
		

}
