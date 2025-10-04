import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class QuickSortTask implements Runnable {

    private int[] array;
    private int start;
    private int end;
    private ExecutorService pool;
    private static final int THRESHOLD = 10000;

    public QuickSortTask(int[] array, int start, int end, ExecutorService pool) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.pool = pool;
    }

    @Override
    public void run() {

        if(start < end) {
            if( (end - start) > THRESHOLD) {
                int q = partition(start, end);

                Future<?> left = pool.submit(new QuickSortTask(array, start, q - 1, pool));
                Future<?> right = pool.submit(new QuickSortTask(array, q + 1, end, pool));

                try {
                    // Will wait for both subtasks to finish
                    left.get();
                    right.get();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    Thread.currentThread().interrupt();
                }

            } else {
                normalQuickSort(start, end);
            }
        }

    }

    public void normalQuickSort(int start, int end) {
        if ( start < end ) {
            int q =  partition(start, end);
            normalQuickSort(start, q - 1);
            normalQuickSort(q + 1, end);
        }
    }

    public int partition(int start, int end) {
        int x = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (array[j] <= x) {
                i++;
                exchange(i, j);
            }
        }
        exchange(i + 1, end);
        return i + 1;
    }


    public void exchange(int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}
