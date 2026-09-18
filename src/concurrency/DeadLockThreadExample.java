package concurrency;

public class DeadLockThreadExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    void methodA(){
        synchronized (lock1){
            IO.println("Thread 1: Holding lock 1");

            synchronized (lock2){
                IO.println("Thread 1: Holding lock 2");
            }
        }
    }

    void methodB(){
        synchronized (lock2){
            IO.println("Thread 2: Holding lock 2");

            synchronized (lock1){
                IO.println("Thread 2: Holding lock 1");
            }
        }
    }

    void main(){
        DeadLockThreadExample deadLockThreadExample = new DeadLockThreadExample();
        new Thread(deadLockThreadExample::methodA).start();
        new Thread(deadLockThreadExample::methodB).start();
    }

}
