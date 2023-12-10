import java.util.ArrayList;
import java.util.Collections;

public class ShortestRemaininTimeFirst {
    private ArrayList<Process> pArrayList;
    private ArrayList<Process> pArrayList2;
    private ArrayList<Process> res;
    private double AvgWaitingTime;
    private double AvgTurnaroundTime;
    public ShortestRemaininTimeFirst(){
        this.pArrayList = new ArrayList<>();
        this.pArrayList2 = new ArrayList<>();
        this.res = new ArrayList<>();
        this.AvgWaitingTime = 0.0;
        this.AvgTurnaroundTime = 0.0;
    } 
    public void Adding(Process p){
        pArrayList.add(p);
    }
    private boolean terminated(){
        for(Process process: pArrayList){
            if(process.remBurstTime != 0){
                return false;
            }
        }
        return true;
    }
    private Process LEASTBurstTime(int currenttime){
        int MinimumBurstTime = 1000;
        int index = 0;
        for(int i = 0; i < this.pArrayList.size() && this.pArrayList.get(i).getArrivalTime() <= currenttime; i++){
            if(this.pArrayList.get(i).remBurstTime < MinimumBurstTime && this.pArrayList.get(i).remBurstTime != 0){
                MinimumBurstTime = this.pArrayList.get(i).remBurstTime;
                index = i;
            }
        }
        return this.pArrayList.get(index);
    }
    private int getIndex(Process p){
        int i = 0;
        while (i < pArrayList.size()) {
            if(this.pArrayList.get(i).getID() == p.getID()){
                return i;
            }
            i++;
        }
        return -1;
    }
    public void RunSRTF(){
        Process p;
        int index;
        int time;
        Collections.sort(pArrayList);
        for(time  = this.pArrayList.get(0).getArrivalTime(); !this.terminated(); ){
            /*p = this.pArrayList.get(0);
            p.remBurstTime--;
            time++;
            index = getIndex(p);
            Process q = this.LEASTBurstTime(time);
            if(p.remBurstTime > q.remBurstTime){
                this.pArrayList.remove(p);
            }
            res.add(p);*/
            p = this.LEASTBurstTime(time);
            index = this.getIndex(p);
            if(this.pArrayList.get(index).remBurstTime != 0){
                this.pArrayList.get(index).StartTime = time;
            }
            while (p.remBurstTime > 0) {
                Process q = this.LEASTBurstTime(time);
                if(!q.getID().equals(p.getID())){
                    break;
                }
                p.remBurstTime--;
                time++;
            }
            this.pArrayList.get(index).WaitingTime += this.pArrayList.get(index).StartTime - this.pArrayList.get(index).getBurstTime();
            this.pArrayList.get(index).TurnaroundTime = this.pArrayList.get(index).WaitingTime + this.pArrayList.get(index).getBurstTime();
            System.out.println("Time: "+ this.pArrayList.get(index).StartTime + " ::Process " + this.pArrayList.get(index).getID() + " => Waiting Time = "+ this.pArrayList.get(index).WaitingTime + ",  TurnAround Time = "+ this.pArrayList.get(index).TurnaroundTime);
            this.pArrayList.get(index).setArrivalTime(time);
            if(pArrayList.get(index).remBurstTime == 0){
                this.pArrayList2.add(p);
                this.pArrayList.remove(p);
            }
            res.add(p);
        }
    }
    public void Printing(){
        double totalwait = 0.0;
        double totalturnaround = 0.0;
        for (Process process : pArrayList2) {
            System.out.println("ID: " + process.getID() + ", Color: " + process.getColor());
            totalwait += process.WaitingTime;
            totalturnaround += process.TurnaroundTime;
        }
        this.AvgWaitingTime = totalwait / pArrayList2.size();
        this.AvgTurnaroundTime = totalturnaround / pArrayList2.size();
        System.out.println("Average Waiting Time = " + AvgWaitingTime);
        System.out.println("Average TurnAround Time = "+ AvgTurnaroundTime);
    }
}
