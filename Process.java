public class Process {
    private String ID;
    private int ArrivalTime;
    private int BurstTime;
    private int PriorityNumber;
    private String color;


    public Process(String ID, int ArrivalTime, int BurstTime, int PriorityNumber) {
        this.ID = ID;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.PriorityNumber = PriorityNumber;
    }

    public String getID() {
        return ID;
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
        this.ID = ID;
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
