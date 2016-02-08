package backend;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String filePath;
	private static int maxFileSize = 50 * 1024;
	private File file;
	private static String hostURL;
	
	private BconDAO bconDAO = new BconDAO();

	public void init() {
		// Get the file location where it would be stored.
		filePath = getServletContext().getInitParameter("file-upload");
		hostURL = getServletContext().getInitParameter("host-url");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);

		// Parse the request
		List<FileItem> items = null;
		
		String responseMsg = null;
		String imageURL = null;
		
		try {
			items = upload.parseRequest(request);
	
			Iterator<FileItem> i = items.iterator();
	
			String token = null;
			int fileId = 0;
		
			Bcon bcon = new Bcon();
			
			while (i.hasNext()) {
				FileItem item = i.next();
				if (item.isFormField()) {
					token = item.getString();
					bcon.setToken(token);
					fileId = bconDAO.getFileId(bcon);
					
				} else {
					file = new File(filePath+fileId);
					item.write(file);
					imageURL = hostURL+filePath+fileId;
					bcon.setImageURL(imageURL);
					bcon.setId(fileId);
					responseMsg = bconDAO.updateImageURL(bcon);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		responseMsg += "\nimageURL="+ imageURL;
		response.getWriter().append(responseMsg);
	}
}