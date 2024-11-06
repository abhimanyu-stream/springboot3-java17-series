package com.stream.completableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureWithRunnableAndExecutorService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {


        ExecutorService executorService = Executors.newFixedThreadPool(8);
        // Implement Runnable using a lambda expression
        Runnable runnableTask = () -> {

                // Simulate a long-running task
            try {
                Thread.sleep(3000); // 3 seconds delay
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Runnable task executed using Lambda");

        };

        // Submit the Runnable task to the executor
        Future<?> future = executorService.submit(runnableTask);

        // Wait until the task is complete
        future.get(); // This will block until the task is done
        System.out.println("Task completed successfully");
        executorService.shutdown();
    }
}
/***
 * The Runnable interface is a functional interface in Java with a single abstract method run().
 * It doesn't return a result and doesn't throw checked exceptions.
 * You can easily implement the Runnable interface using a lambda expression, and then submit it to an ExecutorService for asynchronous execution.
 * Even though Runnable doesn't return a result directly, you can still submit it to an ExecutorService using the submit() method,
 * which will return a Future<?> that can be used to check if the task is complete.
 *
 *
 *
 * In Java, the Runnable interface represents a task that can be executed by a thread or an executor. Unlike Callable,
 * Runnable does not return a result or throw a checked exception. However, you can still obtain a Future object when submitting a Runnable to an ExecutorService.
 * The Future will not carry any meaningful result but can be used to track the execution status and to check if the task has completed.
 *
 *
 * public interface Runnable {
 *     void run();
 * }
 *
 * */
