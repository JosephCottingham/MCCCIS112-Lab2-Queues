
import ch04.threads.*;
import jdk.jshell.execution.Util;
import ch04.queues.*;
import support.LLNode;
import java.lang.Thread;
import java.util.HashMap;
import java.util.UUID;

public class TicketQueue implements Runnable {
   protected LinkedQueue<HashMap<String, String>> processedOrders;
   protected LinkedQueue<HashMap<String, String>> newOrders;
   protected Counter counter;
   protected int maxTicketNum;

   public TicketQueue(LinkedQueue<HashMap<String, String>> newOrders, int maxTicketNum)
   {
      this.processedOrders = new LinkedQueue<HashMap<String, String>>();
      this.newOrders = newOrders;
      this.counter = new Counter();
      this.maxTicketNum = maxTicketNum;
   }

   public void run() {
      while (counter.getCount() < maxTicketNum) {
         try {
            Thread.sleep(10000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         System.out.println("\n\nInitializing Tix Queue...");
         System.out.println(String.valueOf(maxTicketNum - counter.getCount()) + " Tickets available for order!");
         if (newOrders.size()>0){
            System.out.println(String.format("%d orders to process", newOrders.size()));
            while (!newOrders.isEmpty()){
               HashMap<String, String> order = newOrders.dequeue();
               for(int x = 0; x < Integer.parseInt(order.get("Quantity")); x++) counter.increment();
               order.put("Order ID", UUID.randomUUID().toString());
               order.put("Status", "true");
               order.put("Processed", java.time.Clock.systemUTC().instant().toString());

               System.out.println("\n\n\nORDER PROCESSED");
               System.out.println("Order ID: " + order.get("Order ID"));
               System.out.println("Name: " + order.get("Name"));
               System.out.println("Quantity: " + order.get("Quantity"));
               System.out.println("Status: " + order.get("Status"));
               System.out.println("Processed: " + order.get("Processed\n\n"));
            }
         } else {
            System.out.println("No orders to process");
         }
         System.out.println(String.format("%d tickets remaining", (maxTicketNum - counter.getCount())));
      }
   }

}