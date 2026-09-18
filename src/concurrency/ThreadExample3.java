package concurrency;

public class ThreadExample3 {
    public static class MyRunnable implements Runnable{

        @Override
        public void run() {
            IO.println("Runnable running");
            IO.println("Runnable finished");
        }
    }

    void main(){
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }
}
