package lecture10;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Runner {

    private Integer count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        IntStream.rangeClosed(1,10000).forEach(i -> count++);
    }
    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("waiting");
        condition.await();
        System.out.println("Woken up");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }
    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        Scanner sc = new Scanner(System.in);
        System.out.println("Press the return key");
        sc.nextLine();
        System.out.println("Got return key");
        condition.signal();
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println(String.format("Count is %d", count));
    }
}
