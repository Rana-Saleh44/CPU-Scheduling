import java.util.*;

public class AG_Scheduling {
    private double AvgWaitingTime = 0;
    private double AvgTurnaroundTime = 0;
    private Map<String,Double> waitingTime = new HashMap<>();
    private int AG_Factor = 0;

    private Queue<Process> WaitingQueue = new PriorityQueue<>(new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());
        }
    });
    private Queue<Process> ReadyQueue = new LinkedList<>();

    private void SortQueue() {
        if (!ReadyQueue.isEmpty()) {
            Queue<Process> temp = new PriorityQueue<>(ReadyQueue.size(), new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    int result = Integer.compare(o1.getAG(), o2.getAG());
                    if (result != 0) {
                        return result;
                    } else {
                        return Integer.compare(o1.getAG(), o2.getAG());
                    }
                }
            });
            temp.addAll(ReadyQueue);
            ReadyQueue.clear();
            ReadyQueue.addAll(temp);
        }
    }

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
        WaitingQueue.add(process);
        ReadyQueue.add(process);
    }

    public void check(int time) {
        for (Process process : WaitingQueue) {
            process.setArrivalTime(process.getArrivalTime() - time);
            if (process.getArrivalTime() >= 0) {
                ReadyQueue.add(process);
            }
        }
    }

    public void RUN_AG() {
        Process process;
        process = ReadyQueue.poll();
        while (process != null) {
            double time = Math.ceil(0.5 * process.getQuantumTime());
            waitingTime.put(process.getID(), (double) Math.max(0,process.getArrivalTime()));
            process.setArrivalTime(0);
            if (process.remBurstTime <= 0) {
                process.setQuantumTime(0);
                System.out.println("Process " + process.getID() + " finished");
                ReadyQueue.remove(process);
                process = ReadyQueue.poll();
            } else if (!ReadyQueue.isEmpty() && process.getAG() > ReadyQueue.peek().getAG()) {
                if (process.getQuantumTime() > ReadyQueue.peek().getArrivalTime() && ReadyQueue.peek().getArrivalTime() != 0) {
                    process.remBurstTime = process.remBurstTime - ReadyQueue.peek().getArrivalTime();
                    process.setQuantumTime(process.getQuantumTime() + (time - ReadyQueue.peek().getArrivalTime()));
                    System.out.println("Process " + process.getID() + " execute-1 " + ReadyQueue.peek().getArrivalTime());
                } else {
                    process.remBurstTime = (int) (process.remBurstTime - time);
                    process.setQuantumTime(process.getQuantumTime() + (process.getQuantumTime() - time));
                    System.out.println("Process " + process.getID() + " execute-2 " + time);
                }
                ReadyQueue.add(process);
                process = ReadyQueue.poll();
            } else {
                if (process.getQuantumTime() > process.remBurstTime) {
                    System.out.println("Process " + process.getID() + " execute-3 " + process.remBurstTime);
                } else {
                    System.out.println("Process " + process.getID() + " execute-4 " + process.getQuantumTime());
                }
                process.remBurstTime = (int) (process.remBurstTime - process.getQuantumTime());
                double x = 0;
                for (Process p : ReadyQueue) {
                    x = x + p.getQuantumTime();
                }
                if(ReadyQueue.size()!=0){
                    x = x / ReadyQueue.size();
                }
                time = Math.ceil(0.1 * x);
                process.setQuantumTime(process.getQuantumTime() + time);
                if (!ReadyQueue.isEmpty()) {
                    ReadyQueue.add(process);
                    process = ReadyQueue.poll();
                }
            }
        }
    }
}
