import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int[] array = {5,4,3,2,1,1,2,3,4,5,0,1};

        // Creating a fix thread pool for QuickSort to use
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // submit a main task
        Future<?> futureTask = pool.submit(new QuickSortTask(array, 0, array.length - 1, pool));

        try {
            // This will wait for the sorting to complete
            futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Shutdown the pool when it's down
        pool.shutdown();

        System.out.println("Completed!");

        for(int i : array) {
            System.out.print(i + " ");
        }

    }

}
