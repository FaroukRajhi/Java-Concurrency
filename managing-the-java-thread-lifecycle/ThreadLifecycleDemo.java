public class ThreadLifecycleDemo {
    
    public static void main(String[] args) throws InterruptedException {
        
        // Example 1: NEW and RUNNABLE states
        System.out.println("=== NEW and RUNNABLE States ===");
        Thread thread1 = new Thread(new Task(), "Thread-1");
        System.out.println("After creation: " + thread1.getState()); // NEW
        
        thread1.start();
        System.out.println("After start(): " + thread1.getState()); // RUNNABLE
        
        Thread.sleep(100); // Give thread time to run
        System.out.println("During execution: " + thread1.getState()); // RUNNABLE or TIMED_WAITING
        
        // Example 2: TIMED_WAITING state
        System.out.println("\n=== TIMED_WAITING State ===");
        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("Thread-2 sleeping for 3 seconds");
                Thread.sleep(3000); // TIMED_WAITING state
                System.out.println("Thread-2 woke up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");
        
        thread2.start();
        Thread.sleep(100); // Ensure thread starts sleeping
        System.out.println("Thread-2 state while sleeping: " + thread2.getState()); // TIMED_WAITING
        thread2.join(); // Wait for thread2 to finish
        
        // Example 3: WAITING state
        System.out.println("\n=== WAITING State ===");
        final Object lock = new Object();
        
        Thread thread3 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread-3 waiting for notification");
                    lock.wait(); // WAITING state
                    System.out.println("Thread-3 received notification");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-3");
        
        thread3.start();
        Thread.sleep(100); // Ensure thread3 enters wait state
        System.out.println("Thread-3 state while waiting: " + thread3.getState()); // WAITING
        
        // Notify thread3
        synchronized (lock) {
            lock.notify();
        }
        Thread.sleep(100);
        System.out.println("Thread-3 after notification: " + thread3.getState()); // TERMINATED
        
        // Example 4: BLOCKED state
        System.out.println("\n=== BLOCKED State ===");
        final Object sharedLock = new Object();
        
        Thread thread4 = new Thread(() -> {
            synchronized (sharedLock) {
                try {
                    System.out.println("Thread-4 acquired lock, holding for 2 seconds");
                    Thread.sleep(2000);
                    System.out.println("Thread-4 releasing lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-4");
        
        Thread thread5 = new Thread(() -> {
            synchronized (sharedLock) {
                System.out.println("Thread-5 acquired lock");
            }
        }, "Thread-5");
        
        thread4.start();
        Thread.sleep(100); // Ensure thread4 acquires lock first
        thread5.start();
        Thread.sleep(100); // Let thread5 try to acquire lock
        System.out.println("Thread-5 state while waiting for lock: " + thread5.getState()); // BLOCKED
        
        thread4.join();
        thread5.join();
        
        // Example 5: Complete lifecycle tracking
        System.out.println("\n=== Complete Lifecycle Tracking ===");
        Thread lifecycleThread = new Thread(() -> {
            System.out.println("Thread running...");
            try {
                Thread.sleep(1000);
                System.out.println("Thread finishing...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Lifecycle-Thread");
        
        System.out.println("State after creation: " + lifecycleThread.getState()); // NEW
        lifecycleThread.start();
        System.out.println("State after start: " + lifecycleThread.getState()); // RUNNABLE
        
        // Track state changes
        while (lifecycleThread.getState() != Thread.State.TERMINATED) {
            System.out.println("Current state: " + lifecycleThread.getState());
            Thread.sleep(200);
        }
        System.out.println("Final state: " + lifecycleThread.getState()); // TERMINATED
    }
    
    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                Thread.sleep(500); // Simulate work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " completed");
        }
    }
}