package com.stream.singleton.pattern;

import java.io.Serial;
import java.io.Serializable;

public class Singleton implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = - 6687221371001001912L;
    private static Singleton instance;


    private Singleton() {
        // Prevent instantiation via reflection
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
    }

    public static Singleton getInstance() {
        //double check implementation
        if (instance == null) {
            synchronized (Singleton.class){
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }


    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Prevent cloning
        throw new CloneNotSupportedException("Cloning is not supported.");
    }


    // Prevent creating new instance during deserialization
    // Prevent deserialization from creating new instance
    protected Object readResolve() {
        return getInstance();
    }


}
