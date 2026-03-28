
public class UserOrDaemonThread {
    
    public static void main(String[] args) {
        // Create and test a user thread (default)
        Thread userThread = new Thread(() -> {
            System.out.println("Running thread: " + Thread.currentThread().getName());
            System.out.println("Is daemon? " + Thread.currentThread().isDaemon());
            System.out.println("Thread type: " + 
                (Thread.currentThread().isDaemon() ? "DAEMON" : "USER"));
        }, "User-Thread");
        
        // Create and test a daemon thread
        Thread daemonThread = new Thread(() -> {
            System.out.println("Running thread: " + Thread.currentThread().getName());
            System.out.println("Is daemon? " + Thread.currentThread().isDaemon());
            System.out.println("Thread type: " + 
                (Thread.currentThread().isDaemon() ? "DAEMON" : "USER"));
        }, "Daemon-Thread");
        daemonThread.setDaemon(true); // Set as daemon BEFORE starting
        
        // Start both threads
        userThread.start();
        daemonThread.start();
        
        // Test main thread
        System.out.println("\n=== Main Thread ===");
        System.out.println("Thread name: " + Thread.currentThread().getName());
        System.out.println("Is daemon? " + Thread.currentThread().isDaemon());
        System.out.println("Thread type: " + 
            (Thread.currentThread().isDaemon() ? "DAEMON" : "USER"));
        
        // Wait for threads to complete (optional)
        try {
            userThread.join();
            daemonThread.join(1000); // Wait 1 second for daemon thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nMain thread exiting - JVM will exit if only daemon threads remain");
    }
}