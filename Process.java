public class Process implements Comparable<Process>{
    private String Name;
    private int ArrivalTime;
    private int BurstTime;
    public int StartTime;
    private int PriorityNumber;
    private String color;
    public long WaitingTime;
    public long TurnaroundTime;
    public int remBurstTime;

    private int AG;
    private double QuantumTime;

    public double getQuantumTime() {
        return QuantumTime;
    }

    public void setQuantumTime(double quantumTime) {
        QuantumTime = quantumTime;
    }

    public int getAG() {
        return AG;
    }

    public void setAG(int AG) {
        this.AG = AG;
    }


    public Process(String Name, int ArrivalTime, int BurstTime, int PriorityNumber, String color) {
        this.Name = Name;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.PriorityNumber = PriorityNumber;
        this.color = color;
        this.StartTime = 0;
        this.WaitingTime = 0;
        this.TurnaroundTime = 0;
        this.remBurstTime = BurstTime;
    }
    @Override
    public int compareTo(Process p){
        return Integer.compare(this.ArrivalTime, p.getArrivalTime());
    }
    public String getID() {
        return Name;
    }

    public String getColor() {
        return color;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getBurstTime() {
        return BurstTime;
    }

    public int getPriorityNumber() {
        return PriorityNumber;
    }
    public void setID(String ID) {
        this.Name = ID;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setArrivalTime(int arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        BurstTime = burstTime;
    }
    public void setPriorityNumber(int priorityNumber) {
        PriorityNumber = priorityNumber;
    }
}
