import java.util.PriorityQueue;

public class ShortestRemaininTimeFirst {
    private PriorityQueue<Process> processes;
    private double AvgWaitingTime;
    private double AvgTurnaroundTime;
    public ShortestRemaininTimeFirst(){
        this.processes = new PriorityQueue<>((n, m) -> Integer.compare(n.getArrivalTime(), m.getArrivalTime()) );
        this.AvgWaitingTime = 0.0;
        this.AvgTurnaroundTime = 0.0;
    } 
    private double getNumberOfProcesses(){
        return processes.size();
    }
    public void Adding(Process p){
        processes.add(p);
    }
    public void RunSRTF(){
        while(!processes.isEmpty()){
            Process p = processes.poll();
            Process p2 = processes.peek();
            int starttime = p.getArrivalTime();
            p.WaitingTime += starttime - p.getArrivalTime();
            p.TurnaroundTime += p.WaitingTime + p.getBurstTime();
            System.out.println("Time: "+ starttime + " ::Process " + p.getID());
            starttime++;
            p.remBurstTime--;
            /*if(p.remBurstTime > 0){
                processes.add(p);
            }*/
            if(starttime == p2.getArrivalTime()){
                processes.add(p);
            }
        }
    }
    public void Printing(){
        double totalwait = 0.0;
        double totalturnaround = 0.0;
        for (Process process : processes) {
            totalwait += process.WaitingTime;
            totalturnaround += process.TurnaroundTime;
        }
        this.AvgWaitingTime = totalwait / getNumberOfProcesses();
        this.AvgTurnaroundTime = totalturnaround / getNumberOfProcesses();
        System.out.println("Average Waiting Time = " + AvgWaitingTime);
        System.out.println("Average TurnAround Time = "+ AvgTurnaroundTime);
    }
}
