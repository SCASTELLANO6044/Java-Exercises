package concurrency;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadExample2 {
    void main(){
        List<Thread> vThreads = new ArrayList<>();

        int vThreadCount = 100_000;

        for (int i = 0; i < vThreadCount; i++) {
            int vThreadIndex = i;
            Thread thread = Thread.ofVirtual().start(()->{
                int result = 1;
                for (int j = 0; j < 10; j++) {
                    result *= (j+1);
                }
                IO.println("Result["+vThreadIndex+"]: "+result);
            });
            vThreads.add(thread);
        }

        for (int i = 0; i < vThreads.size(); i++) {
            try {
                vThreads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
