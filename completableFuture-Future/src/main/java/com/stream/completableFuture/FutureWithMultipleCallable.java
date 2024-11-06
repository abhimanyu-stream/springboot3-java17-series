package com.stream.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureWithMultipleCallable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // Create a list of Callable tasks
        List<Callable<Integer>> taskList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final int number = i;
            taskList.add(() -> number * number);
        }

        // Create an ExecutorService with a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Submit all tasks to the ExecutorService
        List<Future<Integer>> futures = executorService.invokeAll(taskList);

        // Retrieve and print results from all Futures
        for (Future<Integer> future : futures) {
            System.out.println("Result: " + future.get());
        }
        executorService.shutdown();
    }

}
/**
 *
 In Java, a Callable interface represents a task that can be executed asynchronously by a thread or an executor service
 and can return a result or throw a checked exception.
 It's part of the java.util.concurrent package and is similar to the Runnable interface, but with the added capability to return a result.

 Key Differences between Callable and Runnable:
 Return Type: A Callable can return a result (V) of a specified type,
 whereas a Runnable cannot return a result (it returns void).
 Exception Handling: A Callable can throw checked exceptions, whereas a Runnable cannot throw checked exceptions.
 * **/