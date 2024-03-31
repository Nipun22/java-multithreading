package lecture7;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    private BlockingQueue<Integer> queue = new ArrayBlockingQueue(10);
    private AtomicBoolean producing = new AtomicBoolean(true);


    public static void main(String[] args) {
        App app = new App();
        Thread producerThread = new Thread(() -> {
            try {
                app.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                app.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumerThread2 = new Thread(() -> {
            try {
                app.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producerThread.start();
        consumerThread.start();
        consumerThread2.start();

        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        app.producing.set(false);
    }

    private void producer() throws InterruptedException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while(producing.get()) {
            queue.put(random.nextInt(100));
        }
    }

    private void consumer() throws InterruptedException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while(!queue.isEmpty()) {
            Thread.sleep(100);
            if (random.nextBoolean()) {
                System.out.println(String.format("Polled Number %d, Queue Size %d", queue.poll(), queue.size()));
            }

        }
    }
}
