package concurrency;

import static java.lang.Thread.sleep;

public class ThreadExample10 {
    void main(){
        Runnable runnable = () ->{
            for (int i = 0; i < 5; i++) {
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

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
