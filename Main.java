import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        ShortestRemaininTimeFirst SRTF = new ShortestRemaininTimeFirst();
        int NumberOfProcesses = Integer.parseInt(input.nextLine().trim());
        int RoundRobinTimeQuantum = Integer.parseInt(input.nextLine().trim());
        int contextswitching = Integer.parseInt(input.nextLine().trim());
        for(int i = 0; i < NumberOfProcesses; i++){
            String ProcessName = input.nextLine().trim();
            String ProcessColor = input.nextLine().trim();
            int ProcessArrivalTime = Integer.parseInt(input.nextLine().trim());
            int ProcessBurstTime = Integer.parseInt(input.nextLine().trim());
            int ProcessPriorityNumber = Integer.parseInt(input.nextLine().trim());
            Process p = new Process(ProcessName, ProcessArrivalTime, ProcessBurstTime, 0, ProcessColor);
            SRTF.Adding(p);
        }
        input.close();
        SRTF.RunSRTF();
        SRTF.Printing();
    }
}
