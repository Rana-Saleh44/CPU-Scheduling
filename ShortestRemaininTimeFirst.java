import java.util.Vector;
import java.util.Collections;

public class ShortestRemaininTimeFirst {
    private Vector<Process> pVector;                      // for Entering processes
    private Vector<Process> pVector2;                     // for complete processes
    private Vector<Process> outputVector;                  // for output processes => remBursttime != 0
    private int starvation = 10;
    private double AvgWaitingTime;
    private double AvgTurnaroundTime;
    public ShortestRemaininTimeFirst(){
        this.pVector = new Vector<>();
        this.pVector2 = new Vector<>();
        this.outputVector = new Vector<>();
        this.AvgWaitingTime = 0.0;
        this.AvgTurnaroundTime = 0.0;
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
    private Process getMaxPriority(){
        int max = 0;
        int index = 0;
        for(int i = 0; i < this.pVector.size(); i++){
            if(this.pVector.get(i).getPriorityNumber() > max){
                max = this.pVector.get(i).getPriorityNumber();
                index = i;
            }
        }
        return this.pVector.get(index);
    }
    
    public void RunSRTF(){
        Process p;
        int index;
        int time;
        int flag = 0;
        Collections.sort(pVector);
        for(time  = this.pVector.get(0).getArrivalTime(); !this.terminated(); ){
            Process p2 = this.getMaxPriority();                                     // getmaxpriority to solve starvation                            
            p = this.LEASTRemainingBurstTime(time);                                 // current process = p
            if(p2.getPriorityNumber() > 0){
                flag = 1;
                index = this.pVector.indexOf(p2);
                this.pVector.get(index).StartTime = time;
                while (p2.remBurstTime > 0) {
                    Process q = this.LEASTRemainingBurstTime(time);                   // nextprocess = q if p arrived to q break the loop
                    if(!q.getID().equals(p2.getID())){
                        this.outputVector.add(p2);                                  // add p2 to outputvector to exit from loop and next process begin starting
                        break;
                    }
                    p2.remBurstTime--;
                    time++;
                }
            }
            else{
                flag = 0;
                index = this.pVector.indexOf(p);
                this.pVector.get(index).StartTime = time;
                while (p.remBurstTime > 0) {
                    Process q = this.LEASTRemainingBurstTime(time);               // nextprocess = q if p arrived to q break the loop
                    if(!q.getID().equals(p.getID())){
                        this.outputVector.add(p);                                // add p2 to outputvector to exit from loop and next process begin starting
                        break;
                    }
                    p.remBurstTime--;
                    time++;
                }
            }
            this.pVector.get(index).WaitingTime += this.pVector.get(index).StartTime - this.pVector.get(index).getArrivalTime();
            this.pVector.get(index).TurnaroundTime = this.pVector.get(index).WaitingTime + this.pVector.get(index).getBurstTime() - this.pVector.get(index).remBurstTime;
            System.out.println("Time: "+ this.pVector.get(index).StartTime + " ::Process " + this.pVector.get(index).getID() + " => Waiting Time = "+ this.pVector.get(index).WaitingTime + ",  TurnAround Time = "+ this.pVector.get(index).TurnaroundTime);
            this.pVector.get(index).setArrivalTime(time);
            for(Process process: outputVector){
                process.WaitingTime++;
                if(process.WaitingTime >= starvation){
                    process.setPriorityNumber(process.getPriorityNumber() + 1);
                }
            }
            if(pVector.get(index).remBurstTime == 0){
                if(flag == 1){
                    this.pVector2.add(p2);
                    this.pVector.remove(p2);
                }
                else{
                    this.pVector2.add(p);
                    this.pVector.remove(p);
                }
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
        this.AvgTurnaroundTime = totalturnaround / pVector2.size();
        System.out.println("Average Waiting Time = " + AvgWaitingTime);
        System.out.println("Average TurnAround Time = "+ AvgTurnaroundTime);
    }
    public Vector<Process> getProcesses(){
        return pVector2;
    }
}
