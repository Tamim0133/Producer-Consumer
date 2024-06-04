
class Q{
    int num;
    boolean valueSet = false;

    public synchronized void put (int num)
    {
        while(valueSet) // Jodi Product Create hoyeche but consume hoy nai , wait 
            try {
                wait();
            } catch (Exception e) { }

        this.num = num; // Product Create korlam
            
        System.out.println("Put : " + num); // Koto Number Product Print Korlam 

        valueSet = true; // Product Ache

        notify(); // Consumer ke notify 
    }

    
    public synchronized void get ()
    {
        while(!valueSet)
            try {
                wait();
            } catch (Exception e) { }

        System.out.println("Get : " + num); // Koto Number Product Consume Korlam 

        valueSet = false; // Product Nai 

        notify(); // Notify Producer to produce more 
    }
}

class Producer implements Runnable{
    Q q;
    public Producer(Q q) // Constructor is called only once 
    {
        this.q = q;
        Thread t = new Thread(this, "Producer");
        t.start();
    }

    public void run()
    {
        int i = 0;
        while(true)
        {
            q.put(i++);   // New Product Produced 
            try{Thread.sleep(2000);}catch(Exception e){}
        }
    }
}
class Consumer implements Runnable{
    Q q;
    public Consumer(Q q)
    {
        this.q = q;
        Thread t = new Thread(this, "Consumer");
        t.start();
    }

    public void run()
    {
        while(true)
        {
            q.get();
            try{Thread.sleep(1000);}catch(Exception e){}
        }
    }
}



public class Main {
    public static void main(String[] args){
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
    }
}
