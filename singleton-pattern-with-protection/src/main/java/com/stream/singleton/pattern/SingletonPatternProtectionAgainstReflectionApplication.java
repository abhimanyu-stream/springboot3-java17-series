package com.stream.singleton.pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Constructor;

@SpringBootApplication
public class SingletonPatternProtectionAgainstReflectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingletonPatternProtectionAgainstReflectionApplication.class, args);



		try {
			//Note:-- 1. uncomment below line to create Singleton object, there after using reflection new object can not be created.
			//Singleton singleton = Singleton.getInstance();





			// Access the private constructor
			Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
			constructor.setAccessible(true); // Bypass private access

			// Create a new instance
			Singleton instance1 = constructor.newInstance();
			System.out.println("New instance created via reflection: " + instance1);


			//System.out.println("hashCode check");
			//System.out.println(singleton.hashCode() == instance1.hashCode());
			//System.out.println("reference check");
			//System.out.println(singleton == instance1);


		} catch (Exception e) {
			System.out.println("Cannot create instance via reflection: " + e.getMessage());
		}






	}
}
/***
 *
 * The Singleton Pattern in Java ensures that a class has only one instance and provides a global point of access to it. However, there are several ways that this pattern can be broken unintentionally or maliciously. Below, we will discuss various methods to break the Singleton pattern, along with program examples for each approach.
 *
 * 1. Using Reflection
 * Reflection allows you to bypass the access control checks and create an instance of a class, even if the constructor is private.
 *
 *
 * 2. Using Cloning
 * The default implementation of Object.clone() can be used to create a copy of the Singleton instance, thus breaking the Singleton guarantee.
 *
 * 3. Using Serialization
 * By default, deserialization can create a new instance of a class. This can be prevented by implementing the readResolve() method.
 *
 *
 * */