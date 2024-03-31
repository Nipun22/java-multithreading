package lecture11;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Runner {
    private Account acc1 = new Account();
    private Account acc2 = new Account();

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock lock, Lock otherLock) throws InterruptedException {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while(true) {
            boolean lockAcquired = false;
            boolean otherLockAcquired = false;
            try {
                lockAcquired = lock.tryLock();
                otherLockAcquired = otherLock.tryLock();
            } finally {
                if (lockAcquired && otherLockAcquired) return;
                else if (lockAcquired) lock.unlock();
                else if (otherLockAcquired) otherLock.unlock();
            }
            Thread.sleep(random.nextInt(1000));
        }
    }
    public void firstThread() {
        Random random = new Random();
        IntStream.rangeClosed(1, 10000).forEach(i -> {

            try {
                acquireLocks(lock1, lock2);
                Account.transfer(acc1, acc2, random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        });
    }

    public void secondThread() {
        Random random = new Random();
        IntStream.rangeClosed(1,10000).forEach(i -> {
            try {
                acquireLocks(lock2, lock1);
                Account.transfer(acc2, acc1, random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        });
    }

    public void finished() {
        System.out.println(String.format("account1 balance %d", acc1.getBalance()));
        System.out.println(String.format("account2 balance %d", acc2.getBalance()));
        System.out.println(String.format("Total balance %d", acc2.getBalance() + acc1.getBalance()));
    }
}
