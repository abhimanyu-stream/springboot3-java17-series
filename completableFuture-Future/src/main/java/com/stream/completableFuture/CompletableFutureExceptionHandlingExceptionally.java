package com.stream.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandlingExceptionally {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
             throw new RuntimeException("Something went wrong");
            //return 100;
        });

        CompletableFuture<Integer> handledFuture = future.exceptionally(ex -> {
            System.out.println("Handling exception: " + ex.getMessage());
            return -1; // Fallback value
        });

        handledFuture.thenAccept(result -> System.out.println("Result: " + result));
    }
}
