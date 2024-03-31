package lecture3;

public class App {

    private int count = 0;
    private synchronized void increment() {
        count++;
    }
    public static void main(String[] args) {
        App app = new App();

        app.doWork();
    }

    public void doWork() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 100000; i++) {
                increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i <= 100000; i++) {
                increment();
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(count);
    }
}
