package com.stream.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuturerunAsync {


    public static void main(String[] args) {

        // Running a task asynchronously
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Task is running asynchronously on thread: " + Thread.currentThread().getName());
        });

        // Block and wait for the task to complete
        future.join();

        System.out.println("Task completed.");

    }
}
/***
 * CompletableFuture is a class in Java that represents a future result of an asynchronous computation.
 * The runAsync method is a static method provided by CompletableFuture that allows you to execute a task asynchronously without returning any result.
 * It runs a task on a separate thread, and you can handle the completion of this task later.
 *
 * Here's how CompletableFuture.runAsync works and a simple example to illustrate its usage:
 *
 *
 * Method Signature:
 *
 *
 * public static CompletableFuture<Void> runAsync(Runnable runnable)
 * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
 * Parameters:
 *
 * Runnable runnable: The task to be executed asynchronously. This task doesn't return any result (hence Runnable).
 * Executor executor: (Optional) If provided, the task will be executed using this Executor. If not provided, the task will be executed using the default ForkJoinPool.commonPool().
 * Return Type:
 *
 * CompletableFuture<Void>: A future that represents the completion of the task. Since Runnable doesn't return a value, the CompletableFuture is of type Void.
 * Usage:
 *
 * It's typically used when you want to execute a task asynchronously, and you don't need to return a value from that task.
 *
 *
 * Explanation:
 * Asynchronous Task: The runAsync method is used to execute a task asynchronously. The task is defined by a lambda expression that prints the current thread's name.
 *
 * CompletableFuture: The runAsync method returns a CompletableFuture<Void>. Since the task is a Runnable, it doesn't return a value, so the future is of type Void.
 *
 * Blocking (Optional): In the example, future.join() is called to block the main thread until the asynchronous task is completed. This is optional and typically done if you need to ensure the task is completed before proceeding.
 *
 * Output: When you run this program, it will print the thread name on which the task is running, followed by a message indicating that the task is completed.
 *
 * This is a basic example to demonstrate the concept. In a real-world application, runAsync might be used to offload time-consuming tasks from the main thread to improve performance and responsiveness.
 *
 * */
