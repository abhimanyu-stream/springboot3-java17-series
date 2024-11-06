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


	//Notes:
	//using @Value annotation in a non-Spring class has no effect. It can be used only in @Component, @Service, @Configuration classes
	//You can’t bind property value to a static variable
	//You can’t bind property value to a local variable

	private static KeyStore keystore = null;

	private static Certificate certificate = null;
	private static KeyPair keyPair = null;
	private static RSAPrivateKey rsaPrivateKey = null;
	private static RSAPublicKey rsaPublicKey = null;


	//private static String keyStorePKCE12Path="classpath:keys\\keystore.p12";
	@Value("${keystorepkce12.location}")
	//private static String keyStorePKCE12Path;
	private static String keyStorePKCE12Path="classpath:keys\\keystore.p12";

	@Value("${keystorepkce12.password}")
	//private static String keyStorePKCE12Password;
	private static String keyStorePKCE12Password="abhimanyu";


	@Value("${keystorepkce12.alias}")
	//private static String keypairPKCE12Alias;
	private static String keypairPKCE12Alias="signjwt";
	@Value("${keystorepkce12.deststore.password}")
	//private  String keyStroePKCE12DestinationStorePassword;
	private  String keyStroePKCE12DestinationStorePassword="abhimanyu";
	@Value("${keystorepkce12.destkey.password}")
	//private  String keyStroePKCE12DestinationKeyPassword;
	private  String keyStroePKCE12DestinationKeyPassword="abhimanyu";
	@Value("${keystorepkce12.storetype}")
	//private static String keyStoreType;
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