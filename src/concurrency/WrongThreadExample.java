package concurrency;

import java.util.concurrent.atomic.AtomicInteger;

class Counter{
    AtomicInteger counter;

    public Counter(){
        this.counter = new AtomicInteger(0);
    }

    public synchronized void increment(){
        this.counter.getAndIncrement();
    }
}

public class WrongThreadExample {
    void main(){

        Counter counter = new Counter();

        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(()->{
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        IO.println(counter.counter);

    }
}
