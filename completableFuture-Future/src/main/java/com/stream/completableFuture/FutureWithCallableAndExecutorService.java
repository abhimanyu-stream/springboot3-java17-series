package com.stream.completableFuture;

import java.util.concurrent.*;

public class FutureWithCallableAndExecutorService {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        // Implement Callable using a lambda expression
        Callable<String> callableTask = () -> {
            // Simulate a long-running task
            Thread.sleep(2000); // 2 seconds delay
            return "Result from Callable using Lambda";
        };
        // Submit the callable task to the executor
        Future<String> future = executorService.submit(callableTask);
        // Get the result (this will block until the task is complete)
        String result = future.get();
        System.out.println("Callable task result: " + result);
        executorService.shutdown();

    }
}
/**
 *
 * In Java, a Callable interface represents a task that can be executed asynchronously by a thread or an executor service and can return a result or throw a checked exception. It's part of the java.util.concurrent package and is similar to the Runnable interface, but with the added capability to return a result.
 *
 * Key Differences between Callable and Runnable:
 * Return Type: A Callable can return a result (V) of a specified type, whereas a Runnable cannot return a result (it returns void).
 * Exception Handling: A Callable can throw checked exceptions, whereas a Runnable cannot throw checked exceptions.
 *
 * public interface Callable<V> {
 *     V call() throws Exception;
 * }
 * V is the result type returned by the call() method.
 * */
