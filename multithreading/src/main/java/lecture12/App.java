package lecture12;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Connection.getInstance().connect();

        ExecutorService executorService = Executors.newCachedThreadPool();

        IntStream.range(0, 200).forEach(i -> executorService.submit(() -> Connection.getInstance().connect()));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}

