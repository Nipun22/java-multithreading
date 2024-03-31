package lecture14;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting.");
        Thread thread = new Thread(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            IntStream.range(1, 10_00_00_000)
                    .takeWhile(i -> {
                        boolean interrupted = Thread.currentThread().isInterrupted();
                        if (interrupted) {
                            System.out.println("Interrupted");
                        }
                        return !interrupted;
                    })
                    .forEach(i -> Math.sin(random.nextDouble()));
        });

        thread.start();
        Thread.sleep(600);

        thread.interrupt();
        thread.join();
        System.out.println("Finished.");
    }
}
