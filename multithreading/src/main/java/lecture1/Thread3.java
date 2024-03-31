package lecture1;

import java.util.stream.IntStream;

public class Thread3 {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0,11).forEach(i -> work(i));
            }
            public void work(int i) {
                System.out.println(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
}
