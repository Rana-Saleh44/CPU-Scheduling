public class Main {
    public static void main(String[] args){
        ShortestRemaininTimeFirst SRTF = new ShortestRemaininTimeFirst();
        Process p1 = new Process("1", 0, 8, 0, "Blue");
        Process p2 = new Process("2", 1, 4, 0, "Red");
        Process p3 = new Process("3", 2, 9, 0, "Green");
        Process p4 = new Process("4", 3, 5, 0, "Yellow");
        SRTF.Adding(p1);
        SRTF.Adding(p2);
        SRTF.Adding(p3);
        SRTF.Adding(p4);
        SRTF.RunSRTF();
        SRTF.Printing();
    }
}
