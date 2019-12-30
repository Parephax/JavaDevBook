package com.brackeen.jdb.generic.test;

import com.brackeen.jdb.generic.ThreadPool;
import java.util.Random;

public class ThreadPoolTest {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Tests the ThreadPool task.");
            System.out.println("Usage: java ThreadPoolTest <numTasks> <numThreads>");
            System.out.println("   numTasks - integer: number of tasks to run.");
            System.out.println("   numThreads - integer: number of threads in the ThreadPool.");
            return;
        }
        int numTasks = Integer.parseInt(args[0]);
        int numThreads = Integer.parseInt(args[1]);

        // create the thread pool
        ThreadPool threadPool = new ThreadPool(numThreads);

        // run example tasks
        for (int i=0; i<numTasks; i++) {
            threadPool.runTask(createTask(i));
        }

        // close the pool and wait for all tasks to finish
        threadPool.join();
    }

    /**
     * Creates a simple Runnable that prints an ID, waits a pseudorandom number (0-500) of milliseconds,
     * then prints the ID again.
     * @param taskID The ID of the task generated.
     * @return Runnable task for threading.
     */
    private static Runnable createTask(final int taskID) {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task " + taskID + ": start");

                // simulate a long-running task
                Random rnd = new Random();
                int pause = rnd.nextInt(500);
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ignored) {
                }

                System.out.println("Task " + taskID + ": end (" + pause + ")");
            }
        };
    }
}
