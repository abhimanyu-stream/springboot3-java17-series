package com.stream.singleton.pattern;

public class SingletonPatternProtectionAgainstCloneApplication {

    public static void main(String[] args) {
        try {
            Singleton instance1 = Singleton.getInstance();
            Singleton instance2 = (Singleton) instance1.clone(); // This will throw an exception
        } catch (CloneNotSupportedException e) {
            System.out.println("Cannot clone Singleton instance: " + e.getMessage());
        }
    }
}
