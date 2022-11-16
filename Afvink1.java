import java.util.ArrayList;
import java.util.Random;

public class Afvink1 {
    public static void main(String[] args) {
        ArrayList<Integer> list = CreateRandomList();
        long total_time = 0;
        int percent_done = 0;
        long start = System.nanoTime();
        ArrayList<Integer> sorted_list = SortWithPivot(list);
        long end = System.nanoTime();
        long time = end - start;
        System.out.println("Elapsed time = "+ time +" ns");
    }
    public static ArrayList<Integer> CreateRandomList() {
        ArrayList <Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0;i <1000000;i++) {
            list.add(random.nextInt(1000000000));
        }
        return list;
    }

    public static ArrayList<Integer> SortWithPivot(ArrayList<Integer> list) {
        int Pivot = list.get(0);
        ArrayList <Integer> bottomlist = new ArrayList<>();
        ArrayList <Integer> toplist = new ArrayList<>();
        for (int i = 1; i < list.size(); i ++) {
            Integer number = list.get(i);
            if (number > Pivot) {
                toplist.add(number);
            } else {
                bottomlist.add(number);
            }
        }
        if (bottomlist.size() > 1) {
            bottomlist = SortWithPivot(bottomlist);
        }
        if (toplist.size() > 1) {
            toplist = SortWithPivot(toplist);
        }
        bottomlist.add(Pivot);
        bottomlist.addAll(toplist);
        return bottomlist;
    }
}
