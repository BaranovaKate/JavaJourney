package by.baranova.javajourney.cache;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class representing a simple cache based on HashMap.
 */
@Component
@Data
public class Cache {

    /** Storage for cache data. */
    private Map<String, Object> hashMap = new ConcurrentHashMap<>();

    /** Maximum size of the cache. */
    private static final int MAX_SIZE = 100;

    /**
     * Adds a value to the cache with the specified key.
     * If the cache reaches its maximum size, it clears.
     *
     * @param key   the key to which the value will be added to the cache
     * @param value the value to be added to the cache
     */
    public void put(final String key, final Object value) {
        hashMap.put(key, value);
        if (hashMap.size() >= MAX_SIZE) {
            hashMap.clear();
        }
    }

    /**
     * Retrieves a value from the cache with the specified key.
     *
     * @param key the key from which the value will be retrieved from the cache
     * @return the value from the cache,
     * or null if the value is not found for the specified key
     */
    public Object get(final String key) {
        return hashMap.get(key);
    }

    /**
     * Clears the entire cache.
     */
    public void clear() {
        hashMap.clear();
    }
}
