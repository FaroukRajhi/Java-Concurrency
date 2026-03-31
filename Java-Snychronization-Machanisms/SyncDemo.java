class Counter
{
    private int  count = 0;

    // using synchronized' ensures only one thread can increment at a time.

    public synchronized void increment()
    {
        count ++;
    }

    public int getCount()
    {
        return count;
    }

}


public class SyncDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        Counter counter = new Counter();

        // Creating two threads that both increment the counter 1000 times

        Runnable task = () -> {
            for(int i = 0; i < 1000; i++)
            {
                counter.increment();
            }
        };


        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();


        t1.join();
        t2.join();

        System.out.println("Final count: "+ counter.getCount());

        // With synchronization, this will ALWAYS be 2000
    }

}