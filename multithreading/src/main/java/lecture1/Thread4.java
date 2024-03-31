package lecture1;

import java.util.stream.IntStream;

public class Thread4 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            IntStream.range(0,11).forEach(i -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            });
        });
        thread1.start();
    }
}
