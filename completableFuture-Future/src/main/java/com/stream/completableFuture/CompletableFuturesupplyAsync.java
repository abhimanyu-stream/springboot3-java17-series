package com.stream.completableFuture;

import java.util.concurrent.ExecutionException;

public class CompletableFuturesupplyAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        // Create a CompletableFuturesupplyAsync that runs a task asynchronously
        java.util.concurrent.CompletableFuture<String> completableFuture = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            // Simulate a long-running task
            try {
                Thread.sleep(3000); // 3 seconds delay
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Hello, World!";
        });

        // Check if the CompletableFuturesupplyAsync is done
        System.out.println("Is the task done? " + completableFuture.isDone()); // Expected to be false

        // Do some other work
        System.out.println("Doing some other work...");

        // Wait for the task to complete and get the result
        String result = completableFuture.join(); // Blocking call to ensure task completion

        // Check again if the CompletableFuturesupplyAsync is done
        //System.out.println("Is the task done? " + completableFuture.isDone()); // Expected to be true
        if(completableFuture.isDone()){
            System.out.println("Result from CompletableFuturesupplyAsync : inside isDone check \n" + result);
        }


    }

}
/***
 * Explanation
 * Asynchronous Task Creation:
 *
 * The CompletableFuturesupplyAsync is created using supplyAsync() which runs a task asynchronously that simulates a delay of 3 seconds and then returns a string "Hello, World!".
 * First isDone() Check:
 *
 * Immediately after starting the asynchronous task, isDone() is called. Since the task hasn't completed yet (due to the 3-second sleep), this will print false.
 * Simulating Other Work:
 *
 * While the task is running asynchronously, you can simulate doing other work by printing a message or executing other code.
 * Waiting for Completion:
 *
 * The join() method is used to block the main thread until the CompletableFuturesupplyAsync completes. This ensures that the task finishes and the result is available.
 * Second isDone() Check:
 *
 * After the join() method returns, isDone() is called again. This time it will return true because the task has completed.
 * Result Output:
 *
 * Finally, the result of the computation is printed.
 * Output
 * When you run the above code, the output will be:
 *
 * plaintext
 *
 * Is the task done? false
 * Doing some other work...
 * Is the task done? true
 * Result from CompletableFuturesupplyAsync: Hello, World!
 * This demonstrates how isDone() can be used to check the completion status of a CompletableFuturesupplyAsync
 *
 *
 *
 *
 * public static <U> CompletableFuturesupplyAsync<U> supplyAsync(Supplier<U> supplier)
 * public static <U> CompletableFuturesupplyAsync<U> supplyAsync(Supplier<U> supplier, Executor executor)
 *
 *
 *
 * CompletableFuture.supplyAsync is a method in Java that allows you to execute a task asynchronously and return a result from that task.
 * Unlike runAsync, which works with Runnable and doesn't return a result,
 * supplyAsync works with Supplier and returns a CompletableFuture containing the result of the computation.
 *
 *
 * Method Signature:
 *
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
 * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
 * Parameters:
 *
 * Supplier<U> supplier: The task to be executed asynchronously. This task produces a result of type U.
 * Executor executor: (Optional) If provided, the task will be executed using this Executor. If not provided, the task will be executed using the default ForkJoinPool.commonPool().
 * Return Type:
 *
 * CompletableFuture<U>: A future that represents the result of the asynchronous computation. The result is of type U.
 * Usage:
 *
 * It is used when you want to perform a task asynchronously and return a result from that task.
 *
 *
 * Explanation:
 * Asynchronous Task with a Result: The supplyAsync method is used to execute a task asynchronously. The task is defined by a lambda expression that returns a result (in this case, a string "Hello, World!").
 *
 * CompletableFuture: The supplyAsync method returns a CompletableFuture<String>. The CompletableFuture will hold the result of the asynchronous computation once it's completed.
 *
 * Blocking (Optional): In the example, future.join() is called to block the main thread until the asynchronous task is completed and the result is available. You can also use future.get() for this purpose. Both methods retrieve the result from the CompletableFuture.
 *
 * Output: When you run this program, it will print the thread name on which the task is running and then print the result "Hello, World!" once the task is completed.
 *
 * Additional Notes:
 * Chaining and Composition: CompletableFuture allows you to chain multiple asynchronous tasks together. For example, you can use thenApply, thenAccept, or thenCompose to perform additional actions once the result is available.
 *
 * Error Handling: You can handle exceptions that occur during the asynchronous computation by using methods like exceptionally, handle, or whenComplete.
 *
 * This method is particularly useful in situations where you need to perform non-blocking, parallel computations and return a result, such as making asynchronous web service calls, performing background computations, or processing data asynchronously.
 *
 * */


