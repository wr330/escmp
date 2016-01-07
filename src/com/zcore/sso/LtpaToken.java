package com.zcore.sso;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author 蒲智军
 * 
 */
public class LtpaToken {

	// LTPA 3DES 密钥
	private String ltpa3DESKey;
	// LTPA 密钥密码
	private String ltpaPassword;
	// LTPAToken值
	private String tokenCipher;
	// 解密后字符串
	private String ltpaPlaintext;
	// UserDN
	private String userDN;
	// RealmName
	private String realmName;
	// 过期时间
	private String outTime;
	// 签名信息
	private String cert;

	public LtpaToken() {

	}

	public LtpaToken(String ltpa3DESKey, String ltpaPassword) {

	}

	/**
	 * 获得安全Key
	 * 
	 * @return
	 * @throws Exception
	 */
	private byte[] getSecretKey() throws Exception {
		// 使用SHA获得key密码的hash值
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(ltpaPassword.getBytes());
		byte[] hash3DES = new byte[24];
		System.arraycopy(md.digest(), 0, hash3DES, 0, 20);
		// 使用0替换后4个字节
		Arrays.fill(hash3DES, 20, 24, (byte) 0);
		// BASE64解码 ltpa3DESKey
		byte[] decode3DES = Base64.decodeBase64(ltpa3DESKey.getBytes());
		// 使用key密码hash值解密已Base64解码的ltpa3DESKey
		return decrypt(decode3DES, hash3DES);

	}

	/**
	 * DESede/ECB/PKC5Padding解方法
	 * 
	 * @param ciphertext
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] ciphertext, byte[] key) throws Exception {
		final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		final KeySpec keySpec = new DESedeKeySpec(key);
		final Key secretKey = SecretKeyFactory.getInstance("TripleDES").generateSecret(keySpec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(ciphertext);

	}

	/**
	 * 解密LtpaToken
	 * 
	 * @param ltpaToken
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptLtpaToken(String ltpaToken, byte[] key) throws Exception {
		// Base64解码LTPAToken
		final byte[] ltpaByteArray = Base64.decodeBase64(ltpaToken.getBytes());
		// 使用key解密已Base64解码的LTPAToken
		return decrypt(ltpaByteArray, key);

	}

	/**
	 * 解密LtpaToken
	 * 
	 * @param ltpaToken
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptLtpaToken(String ltpaToken) throws Exception {
		// Base64解码LTPAToken
		final byte[] ltpaByteArray = Base64.decodeBase64(ltpaToken.getBytes());
		// 使用key解密已Base64解码的LTPAToken
		return decrypt(ltpaByteArray, getSecretKey());

	}

	public void getInstance(String ltpa3DESKey, String ltpaPassword, String ltpaToken) throws Exception {
		this.ltpa3DESKey = ltpa3DESKey;
		this.ltpaPassword = ltpaPassword;
		this.tokenCipher = ltpaToken;
		this.ltpaPlaintext = new String(decryptLtpaToken(this.tokenCipher));
		String[] texts = ltpaPlaintext.split("%");
		this.realmName = texts[0].split("/")[0].split(":")[2];
		this.userDN = texts[0].split("/")[1];
		this.outTime = texts[1];
		this.cert = texts[2];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// LTPA 3DES 密钥
			String ltpa3DESKey = "/PhcadrrJHa6s9Za9ochKj74gyuzG86IugJ/6+rwOcY=";
			// LTPA 密钥密码
			String ltpaPassword = "gkhbgkhb";
			// LTPA Cookies
			String tokenCipher =   "SE4AW4QSYHNCHfsD/IhWJZ3bQQbBWpUsXjgaZ2fmhX4x2LqpAtYU0VoIsiMFwl591gmuXiQ+JYo//kghfXja7zj/23XDpOHlF6tlc0emq4Og87e1NCsbrVXcYmIp2Vlho+S6Brxl3JuoiS8b1MNYToVSZUh9CSmT5IsRSlGZ/INO7jwa70qNHiUhNce4aKMqYg8opiRT2zKIVXl/dhjRnsP+/VdrhEx29YhPFvLItgkcMuDb0FP257vdZg1rMwbnTlN+6PKJT/39Gyy01TmQX9kc/VisyCCIlI2UfN4GrLW/qolDFvIQcTgo34QFhq7mDe371Ni6SdwA0pOuenve62rgxUlD+bf+fxCIiM4tT78=";
			LtpaToken token = new LtpaToken();
			
			token.getInstance(ltpa3DESKey, ltpaPassword, tokenCipher);
			
			System.out.println("-->" + token.getLtpa3DESKey());
			System.out.println("-->" + token.getLtpaPassword());
			System.out.println("-->" + token.getTokenCipher());
			System.out.println("-->" + token.getLtpaPlaintext());
			System.out.println("-->" + token.getUserDN());
			System.out.println("-->" + token.getRealmName());
			System.out.println("-->" + token.getOutTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLtpa3DESKey() {
		return ltpa3DESKey;
	}

	public void setLtpa3DESKey(String ltpa3desKey) {
		ltpa3DESKey = ltpa3desKey;
	}

	public String getLtpaPassword() {
		return ltpaPassword;
	}

	public void setLtpaPassword(String ltpaPassword) {
		this.ltpaPassword = ltpaPassword;
	}

	public String getTokenCipher() {
		return tokenCipher;
	}

	public void setTokenCipher(String tokenCipher) {
		this.tokenCipher = tokenCipher;
	}

	public String getUserDN() {
		return userDN;
	}

	public void setUserDN(String userDN) {
		this.userDN = userDN;
	}

	public String getRealmName() {
		return realmName;
	}

	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getLtpaPlaintext() {
		return ltpaPlaintext;
	}

	public void setLtpaPlaintext(String ltpaPlaintext) {
		this.ltpaPlaintext = ltpaPlaintext;
	}
}
