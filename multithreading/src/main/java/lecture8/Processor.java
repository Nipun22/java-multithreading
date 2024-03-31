package lecture8;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Processor {
    public void produce() throws InterruptedException{
        synchronized (this) {
            System.out.println("Producer thread running .... ");
            wait();
            System.out.println("Resumed and completed ... ");
        }
    }

    public void consume() throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
                System.out.println("Waiting for return key");
                sc.nextLine();
                notify();
                Thread.sleep(5000);
                System.out.println("returned key pressed");
        }
    }
}
