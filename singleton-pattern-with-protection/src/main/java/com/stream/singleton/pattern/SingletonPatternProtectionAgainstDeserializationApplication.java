package com.stream.singleton.pattern;

import java.io.*;

public class SingletonPatternProtectionAgainstDeserializationApplication {

    public static void main(String[] args) {
        try {



            Singleton instance1 = Singleton.getInstance();

            // Serialize Singleton
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
            out.writeObject(instance1);
            out.close();

            // Deserialize Singleton
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("singleton.ser"));
            Singleton instance2 = (Singleton) in.readObject();
            in.close();

            // Show both instances
            System.out.println("Instance 1: " + instance1);
            System.out.println("Instance 2: " + instance2);
            System.out.println("Are both instances same? " + (instance1 == instance2));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

