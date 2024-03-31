package lecture5;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class Processor implements Runnable {
    private int id;
    Processor(int id) {
        this.id = id;
    }
    public void run() {
        System.out.println("Starting id: " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed id: " + id);
    }
}

public class App {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.rangeClosed(1,10).forEach(i -> executorService.submit(new Processor(i)));
        executorService.shutdown();
        System.out.println("All tasks submitted");
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks completed");
    }
}
