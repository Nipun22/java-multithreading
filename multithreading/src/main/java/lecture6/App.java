package lecture6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

class Processor implements  Runnable {
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        latch.countDown();
        System.out.println("latch count in Processor " + latch.getCount());
    }
}

public class App {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        IntStream.rangeClosed(1,3).forEach(i -> executorService.submit(new Processor(latch)));
        latch.countDown();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            latch.await();
            System.out.println("latch count in await " + latch.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");
        executorService.shutdown();
    }
}
