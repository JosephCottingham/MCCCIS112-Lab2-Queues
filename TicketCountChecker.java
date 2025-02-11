import ch04.threads.Counter;
import java.lang.Thread;
import javax.swing.*;
import java.util.Arrays;
//Unique feature: Dynamic GUI that limits user ticket quantity to amount of tickets available at purchase
public class TicketCountChecker implements Runnable {
    Counter counter;
    int lastCheck;
    Boolean running;
    int ticketNum;
    SpinnerListModel quanitySpinnerListModel;
    public TicketCountChecker(Counter counter, Boolean running, SpinnerListModel quanitySpinnerListModel, int ticketNum) //ticket counter to keep track of remaining tickets
    {
        this.counter = counter;
        this.lastCheck = counter.getCount();
        this.running = running;
        this.quanitySpinnerListModel = quanitySpinnerListModel;
        this.ticketNum = ticketNum;
    }

    public void run() {
        quanitySpinnerListModel.setList(Arrays.asList(genTicketCountStringArray()));
        while (running) {
            try {
               Thread.sleep(5000);// wait time between processing orders
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            if (counter.getCount() != lastCheck) quanitySpinnerListModel.setList(Arrays.asList(genTicketCountStringArray()));
        }
    }

    private String[] genTicketCountStringArray(){
        String[] countStringArray = new String[ticketNum - counter.getCount()];
        for (int x = 1; x <= ticketNum - counter.getCount(); x++) {
           String val = String.valueOf(x);
           if (x < 10) val = " " + val;
           if (x < 100) val = " " + val;
           countStringArray[x-1] = val;
        }
        return countStringArray;
     }
}
