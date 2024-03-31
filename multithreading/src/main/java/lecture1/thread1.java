package lecture1;

import java.util.stream.IntStream;

class Runner extends Thread {
    public void run() {
        IntStream.range(0, 11).forEach(i -> work(i));
    }

    public void work(int i) {
        System.out.println(i);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class thread1 {
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.start();
        Runner runner1 = new Runner();
        runner1.start();
    }
}
