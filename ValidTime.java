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
        return earliestAlgorithm(false,A,B,C,D,E,F); //brute
    }
    public static String earliestAlgorithm(boolean verbose, int A, int B, int C, int D, int E, int F){
        LinkedList<Integer> order = new LinkedList(Arrays.asList(A,B,C,D,E,F));
        Collections.sort(order);
        LinkedList<Integer> best = new LinkedList<Integer>();
        // HH:Mm:Ss
        best.add(order.remove(0)); // H
        best.add(order.remove(0)); // h
        if(hours(best)>23) return INVALID; // not valid 0-23
        else if(hours(best)<20) {
            if(!(order.get(1)<6)) best.add(1,order.remove(1)); // at least two numbers < 5
        }
        if(best.size() == 2) best.add(order.remove(0)); // ensure Hh:M is chosen
        if(order.get(0)>5) return INVALID; // if no valid number for S is available
        if(!(order.get(1)<6)) best.add(order.remove(1)); // if only one valid S, set m
        while(order.size()>0) best.add(order.remove(0)); // plug in the rest
        return string(best);
    }
    public static String earliestBrute(boolean verbose, int A, int B, int C, int D, int E, int F){
        List<Integer> current = Arrays.asList(A,B,C,D,E,F);
        LinkedList<Integer> order = new LinkedList(current);
        Collections.sort(order,Collections.reverseOrder());
        brute(current, order, 0, verbose);
        if(!valid(order)) return INVALID;
        return string(order);
    }
    public static void brute(List<Integer> current, LinkedList<Integer> best, int index, boolean verbose){
        for(int i = index; i<current.size(); i++){
            Collections.swap(current, i, index); // move
            brute(current,best,index+1,verbose);
            Collections.swap(current, index, i); // undo
        }
        if(index == current.size()-1 && valid(current) && value(current)<=value(best)){
            if(verbose)System.out.printf("Current: %s\nBest: %s\n",current,best);
            best.removeAll(current);
            best.addAll(current);
        }
    }
    public static void main(String... args){
        System.out.println("Test Begin");
        int span = 10000;
        long maxBrute = 0;
        long minBrute = Long.MAX_VALUE;
        long maxAlgorithm = 0;
        long minAlgorithm = Long.MAX_VALUE;
        Random rand = new Random();
        long totalTimeBrute = 0;
        long totalTimeAlgorithm = 0;
        int correct = 0;
        int total = 0;
        for(int i = 0;i<span;i++){
            total++;
            int A = rand.nextInt(10);
            int B = rand.nextInt(10);
            int C = rand.nextInt(10);
            int D = rand.nextInt(10);
            int E = rand.nextInt(10);
            int F = rand.nextInt(10);
            long startBrute = System.nanoTime();
            String bruteResult = earliestBrute(false,A,B,C,D,E,F);
            long endBrute = System.nanoTime();
            long startAlgorithm = System.nanoTime();
            String algorithmResult = earliestAlgorithm(false,A,B,C,D,E,F);
            long endAlgorithm = System.nanoTime();
            if(bruteResult.equals(algorithmResult)) correct++;
            if(!bruteResult.equals(algorithmResult)){
                System.out.println("~~~~~");
                earliestAlgorithm(true,A,B,C,D,E,F);
                System.out.printf("%d %d %d %d %d %d\nBRUTE: %s\nALGO: %s\n~~~~~\n",A,B,C,D,E,F, bruteResult, algorithmResult);
            }
            if(endBrute-startBrute<minBrute)minBrute=endBrute-startBrute;
            if(endBrute-startBrute>maxBrute)maxBrute=endBrute-startBrute;
            totalTimeBrute += endBrute-startBrute;
            if(endAlgorithm-startAlgorithm<minAlgorithm)minAlgorithm=endAlgorithm-startAlgorithm;
            if(endAlgorithm-startAlgorithm>maxAlgorithm)maxAlgorithm=endAlgorithm-startAlgorithm;
            totalTimeAlgorithm += endAlgorithm-startAlgorithm;
        }
        System.out.println("Runs: "+span);
        System.out.println("Average(Brute): "+totalTimeBrute/span + " nanoseconds");
        System.out.println("Maximum(Brute): "+maxBrute+" nanoseconds");
        System.out.println("Minimum(Brute): "+minBrute+" nanoseconds");
        System.out.println("Average(Algorithm): "+totalTimeAlgorithm/span + " nanoseconds");
        System.out.println("Maximum(Algorithm): "+maxAlgorithm+" nanoseconds");
        System.out.println("Minimum(Algorithm): "+minAlgorithm+" nanoseconds");
        System.out.println("Algorithm was correct: "+(100*((float)correct/total))+ "%");
        System.out.println("Test End");
    }
}
