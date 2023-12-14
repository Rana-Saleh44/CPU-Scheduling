import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        ShortestRemaininTimeFirst SRTF = new ShortestRemaininTimeFirst();
        System.out.print("Number of processes: ");
        int NumberOfProcesses = Integer.parseInt(input.nextLine().trim());
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
            SRTF.Adding(p);
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Shortest Remaining Time First Schedule:");
        SRTF.RunSRTF();
        SRTF.Printing();
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Enter number of processes in AG Scheduling: ");
        NumberOfProcesses = Integer.parseInt(input.nextLine().trim());
        System.out.print("Round Robin Time Quantum: ");
        int RoundRobinTimeQuantum = Integer.parseInt(input.nextLine().trim());
        AG_Scheduling ag_scheduling = new AG_Scheduling();
        for(int i = 0 ;i < NumberOfProcesses;i++){
            System.out.print("Process Name " + (i + 1) + ": ");
            String ProcessName = input.nextLine();
            System.out.print("Process Color " + (i + 1) + ": ");
            String ProcessColor = input.nextLine();
            System.out.print("Process Arrival Time " + (i + 1) + ": ");
            int ProcessArrivalTime = input.nextInt();
            input.nextLine();
            System.out.print("Process Burst Time " + (i + 1) + ": ");
            int ProcessBurstTime = input.nextInt();
            input.nextLine();
            System.out.print("Process Priority Number " + (i + 1) + ": ");
            int ProcessPriorityNumber = input.nextInt();
            input.nextLine();
            Process p = new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, 0, ProcessColor);
            ag_scheduling.Adding_Process(p,RoundRobinTimeQuantum);
        }
        System.out.println("--------------------------------------------------------------------------------------");
        ag_scheduling.RUN_AG();
        schedulingGUI schedulingGUI = new schedulingGUI(SRTF.getProcesses());
    }
}
