class SharedBuffer
{
    private int data;
    private boolean hasData = false;

    // The 'synchronized' keyword tells the thread to acquire this object's monitor
    
    public synchronized void produce(int value) throws InterruptedException
    {
        //If there's already data, the producer must wait

        while(hasData)
        {
            System.out.println("Producer waiting...");
            wait(); // Releases the monitor and enters the wait Set
        }

        data = value;
        hasData = true;
        System.out.println("Produced: " + data);


        // Notify the consumer that data is ready
        notify();
    }


    public synchronized void consume() throws InterruptedException
    {
        // If there's data, the consumer must wait

        while(!hasData)
        {
            System.out.println("Consumer waiting..");
            wait();
        }

        hasData = false;
        System.out.println("Consumed: " + data);


        // Notify the producer that the buffer is now empty

        notify();
    }
}


public class MonitorExample
{
    public static void main(String[] args)
    {
        SharedBuffer buffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try
            {
                for(int i = 1; i <= 5; i++) buffer.produce(i);
            }catch (InterruptedException e) { e.printStackTrace();}
        });

        Thread consumer = new Thread(() -> {
            try
            {
                for (int i = 1; i <= 5; i++) buffer.consume();
            }catch(InterruptedException e) {e.printStackTrace();}
        });

        producer.start();
        consumer.start();
    }
}