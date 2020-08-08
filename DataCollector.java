
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
            order.put("Name", nameField.getText());
            String[] split = quanityField.getValue().toString().split(" ");
            order.put("Quantity", split[split.length-1]);
            queue.enqueue(order);
            nameField.setText("");
            quanityField.setValue(" 1");
         }
      } while(true);
   }

}