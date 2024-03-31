package lecture4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Worker {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    private void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    private void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    public void process() {
        IntStream.rangeClosed(0,1000).forEach((i) -> {
            stageOne();
            stageTwo();
        });
    }

    public void main() {
        System.out.println("starting...");

        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> process());
        Thread thread2 = new Thread(() -> process());
        thread.start();
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken = "+ (end-start));
        System.out.println("list1 size " + list1.size());
        System.out.println("list2 size " + list2.size());
    }
}
