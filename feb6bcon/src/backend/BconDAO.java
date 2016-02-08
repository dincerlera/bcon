package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BconDAO {

	protected String signup(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con.prepareStatement("insert into bcons (fullname, password) values (?,?)");

		String fullname = bcon.getFullname();
		String password = bcon.getPassword();

		pst.setString(1, fullname);
		pst.setString(2, password);

		int isInserted = pst.executeUpdate();

		String responseMsg = null;

		if (isInserted == 1) {
			String token = Secure.getToken();
			String sql = "update bcons set token=? where id=last_insert_id()";
			PreparedStatement pst2 = con.prepareStatement(sql);
			pst2.setString(1, token);
			int raws = pst2.executeUpdate();
			if (raws == 1) {
				responseMsg = "message=success" + "\ntoken=" + token;
			} else {
				responseMsg = "message=success" + "\ntoken=error";
			}
		} else {
			responseMsg = "message=failed";
		}

		pst.close();
		DB.getDB().disconnect();

		return responseMsg;

	}

	protected String login(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con
				.prepareStatement("select id, fullname, password from bcons  where fullname=? and password=?");

		String fullname = bcon.getFullname();
		String password = bcon.getPassword();

		pst.setString(1, fullname);
		pst.setString(2, password);

		ResultSet rs = pst.executeQuery();

		String responseMsg = null;

		if (rs.next()) {
			String token = Secure.getToken();
			int id = rs.getInt(1);
			String sql = "update bcons set token=? where id=?";
			PreparedStatement pst2 = con.prepareStatement(sql);
			pst2.setInt(2, id);
			pst2.setString(1, token);

			int raws = pst2.executeUpdate();
			if (raws == 1) {
				responseMsg = "message=success" + "\ntoken=" + token;
			} else {
				responseMsg = "message=success" + "\ntoken=error";
			}
		} else {
			responseMsg = "message=failed";
		}

		pst.close();
		DB.getDB().disconnect();

		return responseMsg;

	}

	protected String update(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con
				.prepareStatement("update bcons set " + "fullname=?, " + "email=?," + "tel=?," + "address=?,"
						+ "company=?," + "title=?," + "city=?," + "country=?," + "searchtext=?" + "where token=?");

		String fullname = bcon.getFullname();
		String email = bcon.getEmail();
		String tel = bcon.getTel();
		String address = bcon.getAddress();
		String company = bcon.getCompany();
		String title = bcon.getTitle();
		String city = bcon.getCity();
		String country = bcon.getCountry();
		String token = bcon.getToken();
		String searchtext = (fullname + " " + address + " " + company + " " + title + " " + city + " " + country).toLowerCase();

		pst.setString(1, fullname);
		pst.setString(2, email);
		pst.setString(3, tel);
		pst.setString(4, address);
		pst.setString(5, company);
		pst.setString(6, title);
		pst.setString(7, city);
		pst.setString(8, country);
		pst.setString(9, searchtext);
		pst.setString(10, token);

		int isUpdated = pst.executeUpdate();

		String responseMsg = null;

		if (isUpdated == 1) {
			responseMsg = "message=success";
		} else {
			responseMsg = "message=failed";
		}

		pst.close();
		DB.getDB().disconnect();

		return responseMsg;

	}

	protected String textquery(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con
				.prepareStatement("select " + "fullname, email, tel, address, company, title, city, country, imageURL "
						+ "from bcons " + "where match(searchtext) " + "against(?) " + "limit 10");

		String searchtext = bcon.getSearchtext();

		pst.setString(1, searchtext);

		ResultSet rs = pst.executeQuery();

		String responseMsg = "";

		if(rs.wasNull()) responseMsg = "message=failed";
		
		int i = 1;
		while (rs.next()) {

			String fullname = (rs.getString(1)!= null) ? rs.getString(1) : "N/A";
			String email = (rs.getString(2)!= null) ? rs.getString(2) : "N/A";
			String tel = (rs.getString(3)!= null) ? rs.getString(3) : "N/A";
			String address = (rs.getString(4)!= null) ? rs.getString(4) : "N/A";
			String company = (rs.getString(5)!= null) ? rs.getString(5) : "N/A";
			String title = (rs.getString(6)!= null) ? rs.getString(6) : "N/A";
			String city = (rs.getString(7)!= null) ? rs.getString(7) : "N/A";
			String country = (rs.getString(8)!= null) ? rs.getString(8) : "N/A";
			String imageURL = (rs.getString(9)!= null) ? rs.getString(9) : "N/A";
			
			
			String item = "\n$listitem=" + i + "\nfullname=" + fullname + "\nemail=" + email+ "\ntel="+ tel + 
					"\naddress=" + address + "\ncompany=" + company + 
							"\ntitle=" + title + "\ncity=" + city + 
							"\ncountry=" + country + "\nimageURL=" + imageURL + "\n";

			System.out.println(item);
			i++;
			responseMsg += item;
		}

		pst.close();
		DB.getDB().disconnect();

		return responseMsg;

	}

	protected int getFileId(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con.prepareStatement("select id from bcons where token=?");
		String token = bcon.getToken();

		pst.setString(1, token);
		ResultSet rs = pst.executeQuery();

		int id = 0;

		if (rs.next()) {
			id = rs.getInt(1);
		}

		pst.close();
		DB.getDB().disconnect();

		return id;

	}

	protected String updateImageURL(Bcon bcon) throws Exception {

		Connection con = DB.getDB().connect();
		PreparedStatement pst = con.prepareStatement("update bcons set imageURL=? where id=?");

		int id = bcon.getId();
		String imageURL = bcon.getImageURL();

		pst.setString(1, imageURL);
		pst.setInt(2, id);

		int isUpdated = pst.executeUpdate();

		String responseMsg = null;

		if (isUpdated == 1) {
			responseMsg = "message=success";
		} else {
			responseMsg = "message=failed";
		}

		pst.close();
		DB.getDB().disconnect();

		return responseMsg;

	}
}
