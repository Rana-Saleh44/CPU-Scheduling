import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;

public class PriorityScheduler {
    public ArrayList<Process> Processes = new ArrayList<>();
    public void Run_PriorityScheduler()
    {
        int waitingTime = 0;
        int tempWait = 0;
        int turnAroundTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        int totalSleepTime = 0;
        Collections.sort(Processes, Comparator.comparingInt(p -> p.getPriorityNumber()));
        for (Process process : Processes)
        {
            tempWait = waitingTime;
            turnAroundTime = tempWait + process.getBurstTime();
          //  waitingTime += process.getBurstTime() + 3;
            if (waitingTime > 10)
            {
                int newPriority = Math.max(process.getPriorityNumber() - 1, 0);
                process.setPriorityNumber(newPriority);
            }
            waitingTime += process.getBurstTime() + 3;
            totalWaitingTime += tempWait;
            totalTurnaroundTime += turnAroundTime;
            System.out.println("-Process Name:" + process.getID());
            System.out.println("-Process color:" + process.getColor());
            System.out.println("-Process arrival time:" + process.getArrivalTime());
            System.out.println("-Process burst time:" + process.getBurstTime());
            System.out.println("-Process priority number:" + process.getPriorityNumber());
            System.out.println("-Process turnaroundtime:" + turnAroundTime);
            System.out.println("-Process waitingtime:" + tempWait);
            System.out.println("\n");
            long startTime = System.currentTimeMillis();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
        double avgWaitingTime = (double) totalWaitingTime / Processes.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / Processes.size();
        System.out.println("\n-Average Waiting Time: " + avgWaitingTime);
        System.out.println("-Average Turnaround Time: " + avgTurnaroundTime);
    }
}


