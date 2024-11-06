package com.stream.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandlingWhenComplete {
    public static void main(String[] args) {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Something went wrong");
        });

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.out.println("Exception occurred: " + ex.getMessage());
            } else {
                System.out.println("Result: " + result);
            }
        }).join(); // Wait for the completion to see the result
    }
}
