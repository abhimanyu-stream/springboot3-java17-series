package com.stream.completableFuture;


import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandlingWithHandle {


    public static void main(String[] args) {
        // Create a CompletableFuture that completes exceptionally
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
             throw new RuntimeException("Something went wrong");
            //return 100;
        });

        // Use handle to process both the result and any exception
        CompletableFuture<Integer> handledFuture = completableFuture.handle((result, ex) -> {
            if (ex != null) {
                // Handle the exception
                System.out.println("Handled exception: " + ex.getMessage());
                return -1;
            } else {
                // Process the result
                return   result;
            }
        });

        // Print the result
        handledFuture.thenAccept(System.out::println);

    }

}

