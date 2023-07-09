package com.quickstart;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * Mã hóa khóa đối xứng: chỉ có một khoá (key) được sử dụng để mã hóa và giải mã dữ liệu.
 * Mã hoá khóa bất đối xứng: sử dụng hai khoá là khóa riêng và khoá công khai.
 * (khóa công khai được sử dụng để mã hóa và khóa riêng được sử dụng để giải mã)
 */
public class Crypter {
    /**
     * Developed: 2000
     * Key Size: 128, 192, 256 bits
     * Algorithm: Đối xứng (Symmetric)
     */
    public static String encryptAES() {
        return "";
    }

    /**
     * Developed: 1977
     * Key Size: 56 bits
     * Algorithm: Đối xứng (Symmetric)
     */
    public static String encryptDES() {
        return "";
    }

    /**
     * Developed: 1977
     * Key Size: >1024 bits
     * Algorithm: Bất đối xứng (Asymmetric)
     */
    public static String encryptRSA(String content) {
        try {
			// Đọc file chứa public key
			FileInputStream fis = new FileInputStream("publicKey.rsa");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();

			// Tạo public key
			X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = factory.generatePublic(spec);

			// Mã hoá dữ liệu
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] encryptOut = cipher.doFinal(content.getBytes());

			return Base64.getEncoder().encodeToString(encryptOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return null;
    }

    /**
     * Developed: 1977
     * Key Size: >1024 bits
     * Algorithm: Bất đối xứng (Asymmetric)
     */
    public static String decryptRSA(String encryptedStr) {
        try {
			// Đọc file chứa private key
			FileInputStream fis = new FileInputStream("privateKey.rsa");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			fis.close();

			// Tạo private key
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = factory.generatePrivate(spec);

			// Giải mã dữ liệu
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] decryptOut = cipher.doFinal(Base64.getDecoder().decode(encryptedStr));
			return new String(decryptOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
        return null;
    }
}
