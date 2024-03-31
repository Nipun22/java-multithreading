package lecture13;

import java.io.IOException;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int duration = random.nextInt(4000);
                if (duration > 2000) {
                    throw new IOException("Sleeping for too long");
                }
                System.out.println("Starting .....");
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished.");
                return duration;
            }
        });
        executorService.shutdown();
//        future.cancel(true);
        while(!future.isDone()) {
            System.out.println("Future not done yet");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println("Thread was asleep for " + future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
