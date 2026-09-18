package concurrency;

public class VirtualThreadExample {
    void main(){
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                IO.println("Index: "+i);
            }
        };

        Thread vThread1 = Thread.ofVirtual().start(runnable);

        Thread vThreadUnstarted = Thread.ofVirtual().unstarted(runnable);

        vThreadUnstarted.start();

        try {
            vThreadUnstarted.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
