import java.util.concurrent.Semaphore;

public class BasicSemaphore {
    
    public static void main(String[] args) {
        // Create a semaphore with 3 permits
        Semaphore semaphore = new Semaphore(3);
        
        // Create 10 threads trying to access the resource
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new Worker(semaphore, i));
            thread.start();
        }
    }
    
    static class Worker implements Runnable {
        private Semaphore semaphore;
        private int workerId;
        
        Worker(Semaphore semaphore, int workerId) {
            this.semaphore = semaphore;
            this.workerId = workerId;
        }
        
        @Override
        public void run() {
            try {
                System.out.println("Worker " + workerId + " is trying to acquire permit...");
                semaphore.acquire(); // Acquire a permit
                
                System.out.println("Worker " + workerId + " acquired permit and is working");
                Thread.sleep(2000); // Simulate work
                
                System.out.println("Worker " + workerId + " releasing permit");
                semaphore.release(); // Release the permit
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}