import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.HashMap;
import java.util.Map;

public class ReadWriteCache<K, V> {
    private final Map<K, V> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    // Write operation - exclusive access
    public void put(K key, V value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + 
                " writing: " + key + "=" + value);
            cache.put(key, value);
            Thread.sleep(100); // Simulate write time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
            System.out.println(Thread.currentThread().getName() + " write completed");
        }
    }
    
    // Read operation - shared access
    public V get(K key) {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + 
                " reading key: " + key);
            V value = cache.get(key);
            Thread.sleep(50); // Simulate read time
            return value;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public static void main(String[] args) {
        ReadWriteCache<String, String> cache = new ReadWriteCache<>();
        
        // Writer thread
        Runnable writer = () -> {
            for (int i = 1; i <= 3; i++) {
                cache.put("Key-" + i, "Value-" + i);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }
        };
        
        // Reader threads
        Runnable reader = () -> {
            for (int i = 1; i <= 5; i++) {
                String value = cache.get("Key-" + (i % 3 + 1));
                System.out.println(Thread.currentThread().getName() + 
                    " got: " + value);
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        };
        
        Thread writer1 = new Thread(writer, "Writer-1");
        Thread reader1 = new Thread(reader, "Reader-1");
        Thread reader2 = new Thread(reader, "Reader-2");
        Thread reader3 = new Thread(reader, "Reader-3");
        
        writer1.start();
        reader1.start();
        reader2.start();
        reader3.start();
    }
}