package backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BconDAO bconDAO = new BconDAO();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String token = request.getParameter("token");
		String fullname = request.getParameter("fullname").trim();
		String email = request.getParameter("email").trim();
		String tel = request.getParameter("tel").trim();
		String company = request.getParameter("company").trim();
		String title = request.getParameter("title").trim();
		String address = request.getParameter("address").trim();
		String city = request.getParameter("city").trim();
		String country = request.getParameter("country").trim();
		  
		Bcon bcon = new Bcon();	
		bcon.setFullname(fullname);
		bcon.setEmail(email);
		bcon.setTel(tel);
		bcon.setAddress(address);
		bcon.setCompany(company);
		bcon.setTitle(title);
		bcon.setCity(city);
		bcon.setCountry(country);
		bcon.setToken(token);
		
		
		String responseMsg = null;
		
		try {
			responseMsg = bconDAO.update(bcon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append(responseMsg);
	}

		
}


