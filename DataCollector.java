
import ch04.queues.*;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.*;

public class DataCollector
{
   static int ticketNum = 100;
   public static void main (String [] args)
   {
      //Directions for the user to purchase tickets.
      String intro = ("Thank you for purchasing your tickets with us! Please follow the directions provided. See box below.");
      System.out.println("There are 100 tickets available for purhcase. The number of tickets available will be updated every 10 seconds.");
      System.out.println("Use the box provided to enter your name and number of tickets you would like. Hit OK to purchase, Cancel to exit.");
      JOptionPane.showMessageDialog(null, intro); 
      
      //Creation of queue to hold tickets
      LinkedQueue<HashMap<String, String>> queue = new LinkedQueue<HashMap<String, String>>();
      TicketQueue ticketThread = new TicketQueue(queue, ticketNum);
      Thread test = new Thread(ticketThread);
      test.start();

      String[] ticketQuanityNumArray = new String[100];
      for (int x = 1; x <= 100; x++) {
         String val = String.valueOf(x);
         if (x < 10) val = " " + val;
         ticketQuanityNumArray[x-1] = val;
      }

      JTextField nameField = new JTextField(5);
      JSpinner quanityField = new JSpinner(new SpinnerListModel(ticketQuanityNumArray));
      JPanel panel = new JPanel();
      panel.add(new JLabel("Name:"));
      panel.add(nameField);
      panel.add(Box.createHorizontalStrut(15)); // a spacer
      panel.add(new JLabel("Quanity:"));
      panel.add(quanityField);

      do {
         int result = JOptionPane.showConfirmDialog(null, panel, "Name and number of tickets: ", JOptionPane.OK_CANCEL_OPTION); //JOptionPane for user to enter information
         if (result == JOptionPane.OK_OPTION) {
            HashMap<String, String> order = new HashMap<String, String>();
            order.put("Name", nameField.getText()); // puts name input in HashMap
            String[] split = quanityField.getValue().toString().split(" ");// splits input to separate name from quantity
            order.put("Quantity", split[split.length-1]); //puts quantity in HashMap
            queue.enqueue(order);// enqueues order onto queue
            nameField.setText("");
            quanityField.setValue(" 1");
         }
      } while(true);
   }

}