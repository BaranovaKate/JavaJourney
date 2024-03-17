package by.baranova.javajourney.cache;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
public class Cache {
    private Map<String, Object> hashMap = new ConcurrentHashMap<>();

    private static final int MAX_SIZE = 100;

    public void put(String key, Object value) {
        hashMap.put(key, value);
        if (hashMap.size() >= MAX_SIZE)
            hashMap.clear();
    }

    public Object get(String key) {
        return hashMap.get(key);
    }

    public void clear() {
        hashMap.clear();
    }
}
