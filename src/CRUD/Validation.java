package CRUD;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class Validation {
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	
	private static final Random RANDOM = new SecureRandom();
	
	static String validatePhone(String phone) {
		if (phone == null) {
			return null;
		}
		phone = phone.trim();
		if (phone.length() != 10) {
			return null;
		}

		for (char c : phone.toCharArray()) {
			if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) {
				return null;
			}
		}
		return phone;
	}

	static int cleanBoolean(String bool) {
		bool = bool.trim();

		if (bool.equals("1")) {
			return 1;
		}
		return 0;
	}

	static int validateInt(String i) {
		int num;
		try {
			num = Integer.parseInt(i);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1;
		}
		return num;
	}

	static Double validateDouble(String d) {
		Double num;
		try {
			num = Double.parseDouble(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -1.0;
		}
		return num;
	}
	
	public static byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}
	
	public static String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}
	
	public static String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}
	
	public static byte[] getBytesFromString(String data) {
		return dec.decode(data);
	}
	
	
	static int validateString(String d) {
		if(d == null){
			return 0;
		}
		return 1;
	}
}
