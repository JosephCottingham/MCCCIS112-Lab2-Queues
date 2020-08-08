import ch04.threads.*;
import ch04.queues.*;
import java.util.HashMap;
import javax.swing.*;

public class DataCollector
{
   static int ticketNum = 100;
   static Counter counter = new Counter();
   static Boolean running = true;
   public static void main (String [] args)
   {
      //Directions for the user to purchase tickets.
      String intro = ("Thank you for purchasing your tickets with us! Please follow the directions provided. See box below.");
      System.out.println("There are 100 tickets available for purhcase. The number of tickets available will be updated every 10 seconds.");
      System.out.println("Use the box provided to enter your name and number of tickets you would like. Hit OK to purchase, Cancel to exit.");
      JOptionPane.showMessageDialog(null, intro); 
      
      //Creation of queue to hold tickets
      LinkedQueue<HashMap<String, String>> queue = new LinkedQueue<HashMap<String, String>>();
      TicketQueue ticketQueue = new TicketQueue(queue, ticketNum, counter, running);
      Thread orderProcessor = new Thread(ticketQueue);
      orderProcessor.start();

      //Creation of GUI
      SpinnerListModel quanitySpinnerListModel = new SpinnerListModel();
      JTextField nameField = new JTextField(5);
      JSpinner quanityField = new JSpinner(quanitySpinnerListModel);
      JPanel panel = new JPanel();
      panel.add(new JLabel("Name:"));
      panel.add(nameField);
      panel.add(Box.createHorizontalStrut(15)); // a spacer
      panel.add(new JLabel("Quanity:"));
      panel.add(quanityField);
      

      //Creation of thread that checks current aviable tickets and confirms that the number of tickets is properly limited
      TicketCountChecker ticketCountChecker = new TicketCountChecker(counter, running, quanitySpinnerListModel, ticketNum);
      Thread countCheckProcessor = new Thread(ticketCountChecker);
      countCheckProcessor.start();

      do {
         int result = JOptionPane.showConfirmDialog(null, panel, "Name and number of tickets: ", JOptionPane.OK_CANCEL_OPTION); //JOptionPane for user to enter information
         if (result == JOptionPane.OK_OPTION)
         {
            HashMap<String, String> order = new HashMap<String, String>();
            order.put("Name", nameField.getText()); // puts name input in HashMap
            String[] split = quanityField.getValue().toString().split(" ");// splits input to separate name from quantity
            order.put("Quantity", split[split.length-1]); //puts quantity in HashMap
            queue.enqueue(order);// enqueues order onto queue
            nameField.setText("");
         }
            
         else 
         {
            String exit = ("No order will be processed at this time. Exiting program."); 
            JOptionPane.showMessageDialog(null, exit); 
            System.exit(0);
         }
               
      }
        while(true); 
   } 
}
