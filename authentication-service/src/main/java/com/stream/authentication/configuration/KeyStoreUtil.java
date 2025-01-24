package com.stream.authentication.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@PropertySource("classpath:application.properties")
public class KeyStoreUtil {




  private  KeyStore keystore = null;

  private  Certificate certificate = null;
  private  KeyPair keyPair = null;
  private  RSAPrivateKey rsaPrivateKey = null;
  private  RSAPublicKey rsaPublicKey = null;
  @Value("${app.jwt.keyStorePKCE12.location}")
  private  String keyStorePKCE12Path;
  //NOTE PLEASE SET app.jwt.keyStorePKCE12-location=classpath:/keys/keystore.p12 PROPERLY , ITS NOT READING THIS FILE, SO i DIRECTLY GIVEN PATH BELOW INTO FileInputStream ARGUMENT
  @Value("${app.jwt.keyStorePKCE12.password}")
  private  String keyStorePKCE12Password;
  @Value("${app.jwt.keypairPKCE12.alias}")
  private  String keypairPKCE12Alias;
  //@Value("${app.jwt.keyStorePKCE12.deststorepassword}")
  //private static String keyStroePKCE12DestinationStorePassword;
  //@Value("${app.jwt.keyStorePKCE12.destkeypassword}")
  //private static String keyStroePKCE12DestinationKeyPassword;
  @Value("${app.jwt.keyStorePKCE12.storetype}")
  private  String keyStoreType;

  public KeyStoreUtil() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext();
    Resource classpathResource = applicationContext.getResource("classpath:keys\\keystore.p12");
    // Load the PKCS12 keystore
    keystore = KeyStore.getInstance("PKCS12");

    InputStream inputStream  = classpathResource.getInputStream();
    char[] password = "abhimanyu".toCharArray(); // Replace with your actual password
    keystore.load(inputStream, password);
    inputStream.close();

    // Get the alias for your key pair
    //String alias = "signjwt"; // Replace with the correct alias

    // Retrieve the private key
    //PrivateKey rsaPrivateKey = (RSAPrivateKey) keystore.getKey(keypairPKCE12Alias, password);
    rsaPrivateKey = (RSAPrivateKey) keystore.getKey("signjwt", password);
    if (rsaPrivateKey instanceof RSAPrivateKey) {
      // Get certificate of public key
      certificate = keystore.getCertificate("signjwt");

      // Get public key
      rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();

      // Return a key pair
      keyPair = new KeyPair(rsaPublicKey, rsaPrivateKey);
    }

    // Retrieve the certificate
    Certificate certificate = keystore.getCertificate("signjwt");
    //PublicKey rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();
    rsaPublicKey = (RSAPublicKey) certificate.getPublicKey();

    // Now you have the private key, public key, and certificate
    System.out.println("RSA Private Key: " + rsaPrivateKey);
    System.out.println("RSA Public Key: " + rsaPublicKey);
    System.out.println("Certificate: " + certificate);
    System.out.println("KeyPair: " + keyPair);
  }


  public void processKeystorep12() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

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



  public  KeyStore getKeystore() {
    return keystore;
  }

  public   Certificate getCertificate() {
    return certificate;
  }

  public  KeyPair getKeyPair() {
    return keyPair;
  }

  public   RSAPrivateKey getRsaPrivateKey() {
    return rsaPrivateKey;
  }

  public  RSAPublicKey getRsaPublicKey() {
    return rsaPublicKey;
  }

  public  String getKeyStoreType() {
    return keyStoreType;
  }
}