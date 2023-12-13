import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.*;

public class PriorityScheduler {
    Process p1 = new Process("Process 1",0,7,3, "Red");
    Process p2 = new Process("Process 2",2,5,1, "Green");
    Process p3 = new Process("Process 3",3,8,2, "Yellow");
    ArrayList<Process> Processes = new ArrayList<>();
    PriorityScheduler()
    {
        int waitingTime = 0;
        int tempWait = 0;
        int turnAroundTime = 0;
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        int totalSleepTime = 0;
        Processes.add(0,p1);
        Processes.add(1,p2);
        Processes.add(2,p3);
        Collections.sort(Processes, Comparator.comparingInt(p -> p.getPriorityNumber()));
        for (Process process : Processes)
        {
            tempWait = waitingTime;
            turnAroundTime = tempWait + process.getBurstTime();
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
    public static void main(String[] args)
    {
        PriorityScheduler p1 = new PriorityScheduler();
    }
}


