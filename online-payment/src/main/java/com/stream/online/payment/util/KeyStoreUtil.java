package com.stream.online.payment.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class KeyStoreUtil {


	//Notes:
	//using @Value annotation in a non-Spring class has no effect. It can be used only in @Component, @Service, @Configuration classes
	//You can’t bind property value to a static variable
	//You can’t bind property value to a local variable

	private  KeyStore keystore = null;

	private  Certificate certificate = null;
	private  KeyPair keyPair = null;
	private  RSAPrivateKey rsaPrivateKey = null;
	private  RSAPublicKey rsaPublicKey = null;



	@Value("${keystorepkce12.location}")
	private String keyStorePKCE12Path;
	//private String keyStorePKCE12Path="classpath:keys\\keystore.p12";

	@Value("${keystorepkce12.password}")
	private String keyStorePKCE12Password;
	//private String keyStorePKCE12Password="abhimanyu";


	@Value("${keystorepkce12.alias}")
	private String keypairPKCE12Alias;
	//private String keypairPKCE12Alias="signjwt";
	@Value("${keystorepkce12.deststore.password}")
	private  String keyStroePKCE12DestinationStorePassword;
	//private  String keyStroePKCE12DestinationStorePassword="abhimanyu";
	@Value("${keystorepkce12.destkey.password}")
	private  String keyStroePKCE12DestinationKeyPassword;
	//private  String keyStroePKCE12DestinationKeyPassword="abhimanyu";
	@Value("${keystorepkce12.storetype}")
	private String keyStoreType;
	//private String keyStoreType="PKCS12";



	public void processKeystorep12() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		Resource classpathResource = applicationContext.getResource(keyStorePKCE12Path);
		// Load the PKCS12 keystore
		keystore = KeyStore.getInstance(keyStoreType);

		InputStream inputStream  = classpathResource.getInputStream();
		char[] password = keyStorePKCE12Password.toCharArray(); // Replace with your actual password
		keystore.load(inputStream, password);
		inputStream.close();



		// Retrieve the private key
		rsaPrivateKey = (RSAPrivateKey) keystore.getKey(keypairPKCE12Alias, password);
		if (rsaPrivateKey instanceof RSAPrivateKey) {
			// Get certificate
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



	public KeyStore getKeystore() {
		return keystore;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}

	public RSAPrivateKey getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public RSAPublicKey getRsaPublicKey() {
		return rsaPublicKey;
	}

	public String getKeyStoreType() {
		return keyStoreType;
	}
}