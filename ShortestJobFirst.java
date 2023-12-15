import java.util.Vector;
import java.util.Collections;

public class ShortestJobFirst {
    private Vector<Process> pVector;                        // for Entering processes
    private Vector<Process> pVector2;                       // for complete processes
    private int contextswitching;
    private double AvgWaitingTime;
    private double AvgTurnAroundTime;

    public ShortestJobFirst(){
        this.pVector = new Vector<>();
        this.pVector2 = new Vector<>();
        contextswitching = 0;
        this.AvgWaitingTime = 0.0;
        this.AvgTurnAroundTime = 0.0;
    }
    public void setContextSwiching(int contextswitching){
        this.contextswitching = contextswitching;
    }
    public void Adding(Process p){
        pVector.add(p);
    }
    private boolean terminated(){
        for(Process process: pVector){
            if(process.remBurstTime != 0){
                return false;
            }
        }
        return true;
    }
    private Process LEASTRemainingBurstTime(int currenttime){
        int MinimumBurstTime = 1000;
        int index = 0;
        for(int i = 0; i < this.pVector.size() && this.pVector.get(i).getArrivalTime() <= currenttime; i++){
            if(this.pVector.get(i).remBurstTime < MinimumBurstTime && this.pVector.get(i).remBurstTime != 0){
                MinimumBurstTime = this.pVector.get(i).remBurstTime;
                index = i;
            }
        }
        return this.pVector.get(index);
    }
    public void RunSJF(){
        Process p;
        int index;
        int time;
        Collections.sort(pVector);
        for(time  = this.pVector.get(0).getArrivalTime(); !this.terminated(); ){
            p = this.LEASTRemainingBurstTime(time); 
            index = this.pVector.indexOf(p);
            this.pVector.get(index).StartTime = time;
            while (p.remBurstTime > 0) {
                p.remBurstTime--;
                time++;
            }
            this.pVector.get(index).WaitingTime += (this.pVector.get(index).StartTime - this.pVector.get(index).getArrivalTime()) + contextswitching;
            this.pVector.get(index).TurnaroundTime = this.pVector.get(index).WaitingTime + this.pVector.get(index).getBurstTime() - this.pVector.get(index).remBurstTime;
            System.out.println("Time: "+ this.pVector.get(index).StartTime + " ::Process " + this.pVector.get(index).getID() + " => Waiting Time = "+ this.pVector.get(index).WaitingTime + ",  TurnAround Time = "+ this.pVector.get(index).TurnaroundTime);
            this.pVector.get(index).setArrivalTime(time);
            if(pVector.get(index).remBurstTime == 0){
                this.pVector2.add(p);
                this.pVector.remove(p);
            }
        }
    }
    public void Printing(){
        double totalwait = 0.0;
        double totalturnaround = 0.0;
        for (Process process : pVector2) {
            System.out.println("ID: " + process.getID() + ", Color: " + process.getColor());
            totalwait += process.WaitingTime;
            totalturnaround += process.TurnaroundTime;
        }
        this.AvgWaitingTime = totalwait / pVector2.size();
        this.AvgTurnAroundTime = totalturnaround / pVector2.size();
        System.out.println("Average Waiting Time = " + AvgWaitingTime);
        System.out.println("Average TurnAround Time = "+ AvgTurnAroundTime);
    }
    public Vector<Process> getProcesses(){
        return pVector2;
    }
}
