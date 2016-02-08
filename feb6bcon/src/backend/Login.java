package backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BconDAO bconDAO = new BconDAO();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fullname = request.getParameter("fullname").trim();
		String plainPassword = request.getParameter("password").trim(); 
		String password = Secure.getHash(plainPassword);
	
		Bcon bcon = new Bcon();	
		bcon.setFullname(fullname);
		bcon.setPassword(password);
		
		String responseMsg = null;
		
		try {
			responseMsg = bconDAO.login(bcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append(responseMsg);
	}
	
}
