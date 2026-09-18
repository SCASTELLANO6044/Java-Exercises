package concurrency;

public class ThreadExample8 {

    public static class StopRunnable implements Runnable{

        private boolean stopRequested = false;

        public synchronized boolean isStopRequested(){
            return this.stopRequested;
        }

        public synchronized void requestStop(){
            this.stopRequested = true;
        }

        private void sleep(long millis){
            try {
                Thread.sleep(millis);
            }catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }

        @Override
        public void run() {
            IO.println("StoppableRunnable running");
            while (!isStopRequested()){
                sleep(1000);
                IO.println("...");
            }
            IO.println("StoppableRunnable stopped");
        }
    }
    void main(){
        StopRunnable stopRunnable = new StopRunnable();
        Thread thread = new Thread(stopRunnable, "The thread");
        thread.start();

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        IO.println("requesting stop");
        stopRunnable.requestStop();
        IO.println("stop requested");

    }
}


