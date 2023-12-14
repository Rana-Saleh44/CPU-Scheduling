import java.util.*;

public class AG_Scheduling {
    private double AvgWaitingTime = 0;
    private double AvgTurnaroundTime = 0;
    private static double CurrentTime = 0;
    private int AG_Factor = 0;
    private Map<String, Double> waitingTime = new HashMap<>();
    private Map<String, Double> Turnaround = new HashMap<>();
    private LinkedList<Integer> arrival = new LinkedList<>();
    private LinkedList<Process> DieList = new LinkedList<>();
    private Map<String, LinkedList<Integer>> HistoryOfQuantum = new HashMap<>();
    private Queue<Process> WaitingQueue = new LinkedList<>();
    private Queue<Process> ReadyQueue = new LinkedList<>();

    public void Adding_Process(Process process, int q) {
        Random random = new Random();
        int Random_Factor = random.nextInt(21);
        if (Random_Factor < 10) {
            AG_Factor = Random_Factor + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
        } else if (Random_Factor > 10) {
            AG_Factor = 10 + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
        } else {
            AG_Factor = process.getPriorityNumber() + process.getArrivalTime() + process.getBurstTime();
            process.setAG(AG_Factor);
            process.setQuantumTime(q);
        }
        WaitingQueue.add(process);
        AddToReadyQueue(4);
        Turnaround.put(process.getID(), process.getQuantumTime() - process.getArrivalTime());
        arrival.add(process.getArrivalTime());
        waitingTime.put(process.getID(), 0.0);
        LinkedList<Integer> quantum = new LinkedList<>();
        HistoryOfQuantum.put(process.getID(), quantum);
    }

    private void AddToReadyQueue(double clock) {
        if (!WaitingQueue.isEmpty() && WaitingQueue.peek().getArrivalTime() <= clock) {
            ReadyQueue.add(WaitingQueue.peek());
            WaitingQueue.remove(WaitingQueue.peek());
        }
    }

    private Process pick() {
        int min_AG = Integer.MAX_VALUE;
        Process p = null;
        if (!ReadyQueue.isEmpty()) {
            for (Process process : ReadyQueue) {
                if (process.getAG() <= min_AG && process.getArrivalTime() <= CurrentTime + 4) {
                    min_AG = process.getAG();
                    p = process;
                }
            }
        }
        return p;
    }

    private void check() {
        for (Process process : ReadyQueue) {
            if (process.remBurstTime <= 0) {
                process.setQuantumTime(0);
                System.out.println("Process " + process.getID() + " finished");
                ReadyQueue.remove(process);
                DieList.add(process);
            }
        }
    }

    public void RUN_AG() {
        Process process;
        process = ReadyQueue.poll();
        while (process != null) {
            check();
            double time = Math.ceil(0.5 * process.getQuantumTime());
            process.setArrivalTime(0);
            if (process.remBurstTime <= 0) {
                process.setQuantumTime(0);
                System.out.println("Process " + process.getID() + " finished");
                ReadyQueue.remove(process);
                DieList.add(process);
                process = ReadyQueue.poll();
            } else if (!ReadyQueue.isEmpty() && pick() != null && process.getAG() > pick().getAG()) {
                if (process.getQuantumTime() > ReadyQueue.peek().getArrivalTime() && ReadyQueue.peek().getArrivalTime() != 0) {
                    process.remBurstTime = process.remBurstTime - ReadyQueue.peek().getArrivalTime();
                    CurrentTime = CurrentTime + ReadyQueue.peek().getArrivalTime();
                    Turnaround.put(ReadyQueue.peek().getID(), CurrentTime - ReadyQueue.peek().getArrivalTime());
                    LinkedList<Integer> ListFroKey = HistoryOfQuantum.get(process.getID());
                    ListFroKey.add((int) process.getQuantumTime());
                    HistoryOfQuantum.put(process.getID(), ListFroKey);
                    process.setQuantumTime(process.getQuantumTime() + (ReadyQueue.peek().getArrivalTime() - time));
                    System.out.println("Process " + process.getID() + " execute " + ReadyQueue.peek().getArrivalTime());
                    ReadyQueue.add(process);
                    process = ReadyQueue.poll();
                } else {
                    process.remBurstTime = (int) (process.remBurstTime - time);
                    CurrentTime = CurrentTime + time;
                    Turnaround.put(process.getID(), CurrentTime);
                    LinkedList<Integer> ListFroKey = HistoryOfQuantum.get(process.getID());
                    ListFroKey.add((int) process.getQuantumTime());
                    HistoryOfQuantum.put(process.getID(), ListFroKey);
                    process.setQuantumTime(process.getQuantumTime() + (process.getQuantumTime() - time));
                    System.out.println("Process " + process.getID() + " execute " + time);
                    ReadyQueue.add(process);
                    process = pick();
                    ReadyQueue.remove(pick());
                }
            } else {
                if (process.getQuantumTime() > process.remBurstTime) {
                    System.out.println("Process " + process.getID() + " execute " + process.remBurstTime);
                    CurrentTime = CurrentTime + process.remBurstTime;
                } else {
                    System.out.println("Process " + process.getID() + " execute " + process.getQuantumTime());
                    CurrentTime = CurrentTime + process.getQuantumTime();
                }
                Turnaround.put(process.getID(), CurrentTime);
                process.remBurstTime = (int) (process.remBurstTime - process.getQuantumTime());
                double mean = 0;
                for (Process p : ReadyQueue) {
                    mean = mean + p.getQuantumTime();
                }
                if (ReadyQueue.size() != 0) {
                    mean = mean / ReadyQueue.size();
                }
                time = Math.ceil(0.1 * mean);
                LinkedList<Integer> ListFroKey = HistoryOfQuantum.get(process.getID());
                ListFroKey.add((int) process.getQuantumTime());
                HistoryOfQuantum.put(process.getID(), ListFroKey);
                process.setQuantumTime(process.getQuantumTime() + time);
                if (!ReadyQueue.isEmpty()) {
                    ReadyQueue.add(process);
                    process = ReadyQueue.poll();
                }
            }
            AddToReadyQueue(CurrentTime);
        }
        System.out.println(CurrentTime);
        double z = 0;
        int i = 0;
        for (Map.Entry<String, Double> entry : Turnaround.entrySet()) {
            z = Turnaround.get(entry.getKey());
            Turnaround.put(entry.getKey(), Math.max((z - arrival.get(i)), 0));
            System.out.println("Process ID: " + entry.getKey() + ", Turnaround Time: " + entry.getValue());
            AvgTurnaroundTime = AvgTurnaroundTime + entry.getValue();
            i++;
        }
        AvgTurnaroundTime = AvgTurnaroundTime / Turnaround.size();
        System.out.println("Average Turnaround Time: " + AvgTurnaroundTime);
        z = 0;
        while (!DieList.isEmpty()) {
            z = Turnaround.get(DieList.peek().getID());
            waitingTime.put(DieList.peek().getID(), (Math.max((z - DieList.peek().getBurstTime()), 0)));
            DieList.poll();
        }
        for (Map.Entry<String, Double> entry : waitingTime.entrySet()) {
            System.out.println("Process ID: " + entry.getKey() + ", Waiting Time: " + entry.getValue());
            AvgWaitingTime = AvgWaitingTime + entry.getValue();
        }
        AvgWaitingTime = AvgWaitingTime / waitingTime.size();
        System.out.println("Average Turnaround Time: " + AvgWaitingTime);
        for (Map.Entry<String, LinkedList<Integer>> entry : HistoryOfQuantum.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

}
