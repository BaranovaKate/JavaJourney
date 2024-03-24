package by.baranova.javajourney.service;
import java.util.concurrent.atomic.AtomicInteger;
public class CounterService {

    private CounterService() {}
    private static AtomicInteger requestCount = new AtomicInteger(0);

    public static synchronized void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    public static synchronized int getRequestCount() {
        return requestCount.get();
    }

}





