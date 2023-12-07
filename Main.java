public class Main {
    public static void main(String[] args){
        ShortestRemaininTimeFirst SRTF = new ShortestRemaininTimeFirst();
        Process p1 = new Process("1", 0, 7, 0);
        Process p2 = new Process("2", 2, 4, 0);
        Process p3 = new Process("3", 4, 1, 0);
        Process p4 = new Process("3", 5, 4, 0);
        SRTF.Adding(p1);
        SRTF.Adding(p2);
        SRTF.Adding(p3);
        SRTF.Adding(p4);
        SRTF.RunSRTF();
        SRTF.Printing();
    }
}
