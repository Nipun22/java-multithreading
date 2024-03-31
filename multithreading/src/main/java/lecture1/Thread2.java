package lecture1;

import java.util.stream.IntStream;

class InterfaceRunner implements Runnable {

    @Override
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
public class Thread2 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new InterfaceRunner());
        Thread thread2 = new Thread(new InterfaceRunner());
        thread1.start();
        thread2.start();
    }

}
