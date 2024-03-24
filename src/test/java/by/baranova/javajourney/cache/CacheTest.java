package by.baranova.javajourney.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CacheTest {

    @InjectMocks
    private Cache cache;

    @Test
    void testPutAndGet_Success() {
        String key = "testKey";
        String value = "testValue";

        cache.put(key, value);

        assertEquals(value, cache.get(key));
    }

    @Test
    void testGet_NotFound() {
        String key = "nonExistentKey";

        Object result = cache.get(key);

        assertNull(result);
    }

    @Test
    void testClear_Success() {
        String key = "testKey";
        String value = "testValue";
        cache.put(key, value);

        cache.clear();

        assertNull(cache.get(key));
    }
}