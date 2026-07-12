package security;

import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class SecurityPassword {
	public static byte[] generateSalt() {	       
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	public static byte[] hashPassword(String password, byte[] salt, int iterations, int keyLength) throws Exception {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		return factory.generateSecret(spec).getEncoded();
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();

	}
	
	public static boolean validatePassword(String password, byte[] salt, String storedHash, int iterations, int keyLength) throws Exception {
	    byte[] hashToCheck = hashPassword(password, salt, iterations, keyLength);
	    return bytesToHex(hashToCheck).equals(storedHash);
	}
}
