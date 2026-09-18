package concurrency;

import static java.lang.Thread.sleep;

public class ThreadExample9 {
    void main() throws InterruptedException {
        Runnable runnable = ()->{
            while (true){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                IO.println("Running");
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        sleep(3100);
    }
}
