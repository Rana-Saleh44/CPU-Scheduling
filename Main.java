import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int option = 1;
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
        for(int i = 0; i < NumberOfProcesses; i++){
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
            ag_scheduling.Adding_Process(p,RoundRobinTimeQuantum);
            priorityScheduler.Processes.add(p2);
        }
        while (option != 0) {
            System.out.println("1) Shortest- Job First\n2) Shortest- Remaining Time First\n3) Priority Scheduling\n4) AG Scheduling\n0) Exit");
            option = Integer.parseInt(input.nextLine().trim());
            switch (option) {
                case 1:
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("Shortest Job First Schedule:");
                    SJF.setContextSwiching(contextSwitching);
                    SJF.RunSJF();
                    SJF.Printing();
                    System.out.println("--------------------------------------------------------------------------------------");
                    break;
                case 2:
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("Shortest Remaining Time First Schedule:");
                    SRTF.RunSRTF();
                    SRTF.Printing();
                    System.out.println("--------------------------------------------------------------------------------------");
                    break;
                case 3:
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("Priority Schedule:");
                    priorityScheduler.Run_PriorityScheduler();
                    break;
                case 4:
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("AG Schedule:");
                    ag_scheduling.RUN_AG();
                    break;
                case 0:
                default:
                    break;
            }
        }
        input.close();
        /*System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Shortest Job First Schedule:");
        SJF.setContextSwiching(contextswitching);
        SJF.RunSJF();
        SJF.Printing();
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Shortest Remaining Time First Schedule:");
        SRTF.RunSRTF();
        SRTF.Printing();
        System.out.println("--------------------------------------------------------------------------------------");*/
    }
}
