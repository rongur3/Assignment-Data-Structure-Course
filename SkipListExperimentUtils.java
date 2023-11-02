import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;

public class SkipListExperimentUtils {
    public static double measureLevels(double p, int x) {
        AbstractSkipList myData=new IndexableSkipList(p);
        int[] array = new int[x];
        double sum = 0;
        long start = System.nanoTime();

        for(int i=0; i < array.length; i++) {
            array[i] = (int)((Math.random() *(1000 - 0 + 1)) + 0);
        }

        for(int i=0; i < array.length; i++) {
            myData.insert(array[i]);
            int height = myData.find(array[i]).height();
            sum = sum + height;
//                System.out.println(array[i]+ "height:" + height);
        }
        long end = System.nanoTime();
        System.out.println("time difference is "+(end-start) +" nano seconds.");
        double avg = sum/x;
        System.out.println("the avg is "+avg);

        return avg;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */

    public static double getDelta(double[] array, double p) {
        double E = 1 / p;
        double sum = -5*E;
        for (int i = 0; i < array.length; i = i + 1){
            sum = sum + array[i];
        }

        double delta = sum*1/5;
        return delta;
    }


    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        AbstractSkipList myData=new IndexableSkipList(p);
        ArrayList<Integer> array = new ArrayList<Integer>(size);

        long total = 0;
        long dur;

        for(int i=0; i <= 2*size; i = i +2){
            array.add(i);
        }
        Collections.shuffle(array);

        for(int i=0; i <= size; i++) {
            long start = System.nanoTime();
            myData.insert(array.get(i));
            long end = System.nanoTime();
            dur = end-start;
            total = total + dur;
        }

        double avg = (double) (total / size);

        Pair<AbstractSkipList, Double> Pair = new Pair<>(myData, avg);

        return Pair;
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        long total = 0;
        long dur;

        for(int i=0; i <= 2*size; i = i +2) {
            long start = System.nanoTime();
            skipList.search(i);
            long end = System.nanoTime();
            dur = end-start;
            total = total + dur;
        }

        double avg = (double) (total / size);

        return avg;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        long total = 0;
        long dur;

        for(int i=0; i <= 2*size; i = i +2) {
            AbstractSkipList.Node curr = skipList.find(i);
            long start = System.nanoTime();
            skipList.delete(curr);
            long end = System.nanoTime();
            dur = end-start;
            total = total + dur;
        }

        double avg = (double) (total / size);

        return avg;
    }

    public static void main(String[] args) {
        double[] p= {0.33,0.5,0.75,0,9};
        int[] x= {1000,2500,5000,10000,15000,20000,50000};
        double sumInsert=0;
        double sumSearch=0;
        double sumDelete=0;
        for(int i=0;i<p.length;i++) {
            for(int j=0;j<x.length;j++) {
                for(int k=0;k<30;k++) {
                    Pair<AbstractSkipList, Double> resultPair = measureInsertions(p[i],x[j]);
                    sumInsert+=resultPair.second();
                    sumSearch+=measureSearch(resultPair.first(),x[j]);
                    sumDelete+=measureDeletions(resultPair.first(),x[j]);
                }
                System.out.println("for the probability "+p[i]+" and size "+x[j]+" average insertion is "+sumInsert/30);
                System.out.println("for the probability "+p[i]+" and size "+x[j]+" average search is "+sumSearch/30);
                System.out.println("for the probability "+p[i]+" and size "+x[j]+" average Delete is "+sumDelete/30);
            }
        }
    }
}
