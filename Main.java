class Q{
    int num;
    boolean produced = false; // Product Produce hoyeche 

    public synchronized void produce (int i) // Keep the method Synchronized 
    {
        while(produced) // Jodi Product Produce Hoye thake then wait 
            try {
                wait();
            } catch (Exception e) { }

        this.num = i; // new Produce Product 

        produced = true; // Prooduct Produce hoyeche 

        System.out.println("Produced : " + this.num); // New Produced Product Number 

        notify(); // Notify Consumer that the product has been produced 
    }

    public synchronized void consume() // Keep the method Sunchronized 
    {
        while (!produced) { // If the product is not produced yet , then wait 
            try {
                wait();
            } catch (Exception e) { }   
        }

        System.out.println("Consumed : " + this.num); // now Consume 

        produced = false;

        notify();    // Notify the Producer that the product has been consumed 
    }
}

class Producer implements Runnable{

    Q q;

    Producer(Q q)
    {
        this.q = q;
    }

    @Override 
    public void run() {
        int i = 0;

        while(true) // Producer Produces Products 
        {
            q.produce(i++);
            try{Thread.sleep(1000);}catch(Exception e){}
        }

    }
}

class Consumer implements Runnable{
    Q q;
    Consumer(Q q)
    {
        this.q = q;
    }

    @Override 
    public void run() {
        while(true) // Consumer Consumed the products 
        {
            q.consume();
            try{Thread.sleep(1000);}catch(Exception e){}
        }
    }
}

public class Main{
    public static void main(String[] args){

        Q q = new Q();
        
        Producer p =  new Producer(q);
        Consumer c =  new Consumer(q);

        Thread producerThread = new Thread(p);
        Thread consumerThread = new Thread(c);

        producerThread.start();
        consumerThread.start();
    }
}
