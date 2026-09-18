public static class MyThread extends Thread {
    public void run() {
        IO.println("Threat running");
        IO.println("Threat finished");
    }
}

void main() {
    MyThread myThread = new MyThread();
    myThread.start();
}
