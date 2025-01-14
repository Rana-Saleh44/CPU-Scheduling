// #FF0000 = red   #00FF00 = green   #0000FF = blue   #FFFF00 = yellow

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ShortestRemaininTimeFirst SRTF = new ShortestRemaininTimeFirst();
        ShortestJobFirst SJF = new ShortestJobFirst();
        AG_Scheduling ag_scheduling = new AG_Scheduling();
        PriorityScheduler priorityScheduler = new PriorityScheduler();
        System.out.print("Number of processes: ");
        int NumberOfProcesses = Integer.parseInt(input.nextLine().trim());
        System.out.print("Round Robin Time Quantum: ");
        int RoundRobinTimeQuantum = Integer.parseInt(input.nextLine().trim());
        System.out.print("context switching: ");
        int contextSwitching = Integer.parseInt(input.nextLine().trim());
        for (int i = 0; i < NumberOfProcesses; i++) {
            System.out.print("Process Name " + (i + 1) + ": ");
            String ProcessName = input.nextLine().trim();
            System.out.print("Process Color " + (i + 1) + ": ");
            String ProcessColor = input.nextLine().trim();
            System.out.print("Process Arrival Time " + (i + 1) + ": ");
            int ProcessArrivalTime = Integer.parseInt(input.nextLine().trim());
            System.out.print("Process Burst Time " + (i + 1) + ": ");
            int ProcessBurstTime = Integer.parseInt(input.nextLine().trim());
            System.out.print("Process Priority Number " + (i + 1) + ": ");
            int ProcessPriorityNumber = Integer.parseInt(input.nextLine().trim());
            Process p = new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, 0, ProcessColor);
            Process p2 = new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, ProcessPriorityNumber, ProcessColor);
            SRTF.Adding(new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, 0, ProcessColor));
            SJF.Adding(new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, 0, ProcessColor));
            ag_scheduling.Adding_Process(p, RoundRobinTimeQuantum);
            priorityScheduler.Processes.add(p2);
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Shortest Job First Schedule:");
        SJF.setContextSwiching(contextSwitching);
        SJF.RunSJF();
        SJF.Printing();
        ProcessColor pc1 = new ProcessColor(SJF.getProcesses());
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Shortest Remaining Time First Schedule:");
        SRTF.RunSRTF();
        SRTF.Printing();
        //ProcessColor pc2 = new ProcessColor(SRTF.getProcesses());
        System.out.println("--------------------------------------------------------------------------------------");

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Priority Schedule:");
        priorityScheduler.Run_PriorityScheduler();

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("AG Schedule:");
        //ag_scheduling.RUN_AG();

        input.close();
    }
}
