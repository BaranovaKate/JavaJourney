package by.baranova.javajourney.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @InjectMocks
    private Cache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testPutAndGet_Success() {
        // Arrange
        String key = "testKey";
        String value = "testValue";

        // Act
        cache.put(key, value);

        // Assert
        assertEquals(value, cache.get(key));
    }

    @Test
    void testGet_NotFound() {
        // Arrange
        String key = "nonExistentKey";

        // Act
        Object result = cache.get(key);

        // Assert
        assertNull(result);
    }

    @Test
    void testClear_Success() {
        // Arrange
        String key = "testKey";
        String value = "testValue";
        cache.put(key, value);

        // Act
        cache.clear();

        // Assert
        assertNull(cache.get(key));
    }
}