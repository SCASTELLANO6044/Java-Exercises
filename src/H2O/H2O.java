package H2O;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/** Coordinate threads into water molecules: two hydrogen atoms and one oxygen. */
public class H2O {

    private Semaphore h;
    private Semaphore o;
    private CyclicBarrier cyclicBarrier;


    public H2O() {
        this.h = new Semaphore(2);
        this.o = new Semaphore(1);
        this.cyclicBarrier = new CyclicBarrier(3);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        try {
            h.acquire();
            cyclicBarrier.await();
            releaseHydrogen.run();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        h.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        try {
            o.acquire();
            cyclicBarrier.await();
            releaseOxygen.run();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        o.release();
    }
}
