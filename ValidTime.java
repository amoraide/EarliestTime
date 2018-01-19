import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Arrays;
public class ValidTime{
    public static final String INVALID = "NOT POSSIBLE";
    public static int hours(List<Integer> o){return o.get(0)*10+o.get(1);}
    public static int minutes(List<Integer> o){return o.get(2)*10+o.get(3);}
    public static int seconds(List<Integer> o){return o.get(4)*10+o.get(5);}
    public static long value(List<Integer> o){return hours(o)*60*60 + minutes(o)*60 + seconds(o);}
    public static boolean valid(List<Integer> o){return hours(o)<24&&minutes(o)<60&&seconds(o)<60;}
    public static String string(List<Integer> o){return ""+hours(o)+"::"+minutes(o)+"::"+seconds(o);}
    public static String earliest(int A, int B, int C, int D, int E, int F){
        return earliestBrute(A,B,C,D,E,F); //brute
    }
    public static String earliestBrute(int A, int B, int C, int D, int E, int F){
        List<Integer> current = Arrays.asList(A,B,C,D,E,F);
        LinkedList<Integer> order = new LinkedList(current);
        brute(current, order, 0);
        if(!valid(order)) return INVALID;
        return string(order);
    }
    public static void brute(List<Integer> current, LinkedList<Integer> best, int index){
        for(int i = index; i<current.size(); i++){
            Collections.swap(current, i, index); // move
            brute(current,best,index+1);
            Collections.swap(current, index, i); // undo
        }
        if(index == best.size()-1 && valid(current)&&value(current)<value(best)){
            best.removeAll(current);best.addAll(current);
        }
    }
    public static void main(String... args){
        System.out.println("Test Begin");
        int MAX = 100;
        Random rand = new Random();
        long totalTime = 0;
        for(int i = 0;i<MAX;i++){
            long start = System.nanoTime();
            String result = earliest(rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10));
            if(result.equals(INVALID)){
                i--;
                continue;
            }
            long end = System.nanoTime();
//            System.out.println("Took "+(end-start)+" nanoseconds");
            totalTime += end-start;

        }
        System.out.println("Average: "+totalTime/MAX + " nanoseconds");
        System.out.println("Test End");
    }
}
