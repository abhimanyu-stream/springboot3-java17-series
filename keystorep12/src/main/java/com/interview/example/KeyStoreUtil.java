package com.interview.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class KeyStoreUtil {




	private static KeyStore keystore = null;

	private static Certificate certificate = null;
	private static KeyPair keyPair = null;
	private static RSAPrivateKey rsaPrivateKey = null;
	private static RSAPublicKey rsaPublicKey = null;

	//@Value("${app.jwt.keyStorePKCE12-location}")
	private static String keyStorePKCE12Path="classpath:keys\\keystore.p12";
	//NOTE PLEASE SET app.jwt.keyStorePKCE12-location=classpath:/keys/keystore.p12 PROPERLY , ITS NOT READING THIS FILE, SO i DIRECTLY GIVEN PATH BELOW INTO FileInputStream ARGUMENT
	//@Value("${app.jwt.keyStorePKCE12-password}")
	private static String keyStorePKCE12Password="abhimanyu";
	//@Value("${app.jwt.keypairPKCE12-alias}")
	private static String keypairPKCE12Alias="signjwt";
	@Value("${app.jwt.keyStorePKCE12.deststore-password}")
	private static String keyStroePKCE12DestinationStorePassword;
	@Value("${app.jwt.keyStorePKCE12.destkey-password}")
	private static String keyStroePKCE12DestinationKeyPassword;
	//@Value("${app.jwt.keyStorePKCE12.storetype}")
	private static String keyStoreType="PKCS12";




	public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
		processKeystorep12();
	}



	public static void processKeystorep12() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		Resource classpathResource = applicationContext.getResource(keyStorePKCE12Path);
		// Load the PKCS12 keystore
		keystore = KeyStore.getInstance(keyStoreType);

		InputStream inputStream  = classpathResource.getInputStream();
		char[] password = keyStorePKCE12Password.toCharArray(); // Replace with your actual password
		keystore.load(inputStream, password);
		inputStream.close();

		// Get the alias for your key pair
		//String alias = "signjwt"; // Replace with the correct alias

		// Retrieve the private key
		//PrivateKey rsaPrivateKey = (RSAPrivateKey) keystore.getKey(keypairPKCE12Alias, password);
		rsaPrivateKey = (RSAPrivateKey) keystore.getKey(keypairPKCE12Alias, password);
		if (rsaPrivateKey instanceof RSAPrivateKey) {
			// Get certificate of public key
			certificate = keystore.getCertificate(keypairPKCE12Alias);

			// Get public key
			rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();

			// Return a key pair
			keyPair = new KeyPair(rsaPublicKey, rsaPrivateKey);
		}

		// Retrieve the certificate
		Certificate certificate = keystore.getCertificate(keypairPKCE12Alias);
		//PublicKey rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
		rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();

		// Now you have the private key, public key, and certificate
		System.out.println("RSA Private Key: " + rsaPrivateKey);
		System.out.println("RSA Public Key: " + rsaPublicKey);
		System.out.println("Certificate: " + certificate);
		System.out.println("KeyPair: " + keyPair);
	}



	public static KeyStore getKeystore() {
		return keystore;
	}

	public static Certificate getCertificate() {
		return certificate;
	}

	public static KeyPair getKeyPair() {
		return keyPair;
	}

	public static RSAPrivateKey getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public static RSAPublicKey getRsaPublicKey() {
		return rsaPublicKey;
	}

	public String getKeyStoreType() {
		return keyStoreType;
	}
}