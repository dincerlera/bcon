package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Secure {
	
	public static String getHash(String input) {
	      StringBuffer sb = new StringBuffer();

	      try {
	         MessageDigest md =
	            MessageDigest.getInstance("SHA-256");
	         md.update(input.getBytes());
	          byte[] mdbytes = md.digest();

	         //convert the byte to hex format
	         for (int i = 0; i < mdbytes.length; i++) {
	            sb.append(Integer.toString((mdbytes[i] & 0xff)
	               + 0x100, 16).substring(1));
	         }
	      } catch (NoSuchAlgorithmException e) {
	         e.printStackTrace();
	         System.exit(-1);
	      }

	      return sb.toString();
	   }
	
	
	private static final SecureRandom random = new SecureRandom();

	public static String getToken() {
		    return new BigInteger(130, random).toString(32);
		  }
		
}
