import javax.print.DocFlavor;
import java.util.*;

public class AG_Scheduling {
    private double AvgWaitingTime = 0;
    private double AvgTurnaroundTime = 0;
    private Map<String, Double> waitingTime = new HashMap<>();
    private Map<String, Double> Turnaround = new HashMap<>();
    private int AG_Factor = 0;

    private Queue<Process> WaitingQueue = new LinkedList<>();
    private Queue<Process> ReadyQueue = new LinkedList<>();

    /*private void AddToReadyQueue(double clock) {
        for (Process process : WaitingQueue) {
            if(process.getArrivalTime() <=0){
                ReadyQueue.add(process);
                WaitingQueue.remove(process);
            }
            else if (process.getArrivalTime() <=clock) {
                ReadyQueue.add(process);
                WaitingQueue.remove(process);
            }
        }
    }*/

    public void Adding_Process(Process process, int q) {
        Random random = new Random();
        int Random_Factor = random.nextInt(21);
        if (Random_Factor < 10) {
            AG_Factor = Random_Factor + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
            System.out.println(AG_Factor);
        } else if (Random_Factor > 10) {
            AG_Factor = 10 + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
            System.out.println(AG_Factor);
        } else {
            AG_Factor = process.getPriorityNumber() + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
            System.out.println(AG_Factor);
        }
        ReadyQueue.add(process);
        Turnaround.put(process.getID(), 0.0);
        waitingTime.put(process.getID(),0.0);
    }

    private Process pick() {
        int min_AG = Integer.MAX_VALUE;
        Process p = null;
        if (!ReadyQueue.isEmpty()) {
            for (Process process : ReadyQueue) {
                if (process.getAG() <= min_AG) {
                    min_AG = process.getAG();
                    p = process;
                }
            }
        }
        return p;
    }

    public void RUN_AG() {
        Process process;
        double CurrentTime = 0;
        double pCurrent = 0;
        double x = 0;
        double y = 0;
        process = ReadyQueue.poll();
        while (process != null) {
            double time = Math.ceil(0.5 * process.getQuantumTime());
            process.setArrivalTime(0);
            if (process.remBurstTime <= 0) {
                process.setQuantumTime(0);
                System.out.println("Process " + process.getID() + " finished");
                ReadyQueue.remove(process);
                process = ReadyQueue.poll();
            } else if (!ReadyQueue.isEmpty() && process.getAG() > pick().getAG()) {
                if (process.getQuantumTime() > ReadyQueue.peek().getArrivalTime() && ReadyQueue.peek().getArrivalTime() != 0) {
                    process.remBurstTime = process.remBurstTime - ReadyQueue.peek().getArrivalTime();
                    CurrentTime = CurrentTime + ReadyQueue.peek().getArrivalTime();
                    x = Turnaround.get(process.getID())+ReadyQueue.peek().getArrivalTime();
                    Turnaround.put(process.getID(),x);
                    process.setQuantumTime(process.getQuantumTime() + (ReadyQueue.peek().getArrivalTime() - time));
                    System.out.println("Process " + process.getID() + " execute-1 " + ReadyQueue.peek().getArrivalTime());
                    ReadyQueue.add(process);
                    process = ReadyQueue.poll();
                } else {
                    process.remBurstTime = (int) (process.remBurstTime - time);
                    CurrentTime = CurrentTime + time;
                    x =Turnaround.get(process.getID()) + time;
                    Turnaround.put(process.getID(), x);
                    process.setQuantumTime(process.getQuantumTime() + (process.getQuantumTime() - time));
                    System.out.println("Process " + process.getID() + " execute-2 " + time);
                    ReadyQueue.add(process);
                    process = pick();
                    ReadyQueue.remove(pick());
                }
            } else {
                if (process.getQuantumTime() > process.remBurstTime) {
                    System.out.println("Process " + process.getID() + " execute-3 " + process.remBurstTime);
                    CurrentTime = CurrentTime + process.remBurstTime;
                    x = Turnaround.get(process.getID()) + process.remBurstTime;
                } else {
                    System.out.println("Process " + process.getID() + " execute-4 " + process.getQuantumTime());
                    CurrentTime = CurrentTime + process.getQuantumTime();
                    x = Turnaround.get(process.getID()) + process.getQuantumTime() ;
                }
                Turnaround.put(process.getID(), x);
                process.remBurstTime = (int) (process.remBurstTime - process.getQuantumTime());
                double mean = 0;
                for (Process p : ReadyQueue) {
                    mean = mean + p.getQuantumTime();
                }
                if (ReadyQueue.size() != 0) {
                    mean = mean / ReadyQueue.size();
                }
                time = Math.ceil(0.1 * mean);
                process.setQuantumTime(process.getQuantumTime() + time);
                if (!ReadyQueue.isEmpty()) {
                    ReadyQueue.add(process);
                    process = ReadyQueue.poll();
                }
            }
            if (ReadyQueue.isEmpty() && process == null) {
                ReadyQueue.add(WaitingQueue.peek());
                process = ReadyQueue.poll();
            }
        }
        System.out.println(CurrentTime);
        for (Map.Entry<String, Double> entry : Turnaround.entrySet()) {
            System.out.println("Process ID: " + entry.getKey() + ", Turnaround Time: " + entry.getValue());
        }
    }
}
