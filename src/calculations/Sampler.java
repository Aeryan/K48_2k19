package calculations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;

public class Sampler {

    // Currently just a wrapper so that the mapper could be worked on

    public static List<Double> main(int size, int area, int startX, int startY, int endX, int endY) {
        int cores = Runtime.getRuntime().availableProcessors();

        double[][] dataset = new double[size][size];
        Random r = new Random();
        for (int i = 0; i < dataset.length; i++) {
            for (int j = 0; j < dataset[i].length; j++) {
                dataset[i][j] = r.nextInt(10);
            }
        }

        // DOWNSAMPLE the given Dataset
        Thread[] threads = new Thread[cores];

        int counter = 0;
        int order = 0;

        DownSamplingWorker[] workers = new DownSamplingWorker[dataset.length/area];

        while (counter < dataset.length) {


            for (int i = 0; i < threads.length; i++) {
                if (counter >= dataset.length) {
                    break;
                }
                double[][] splice = Arrays.copyOfRange(dataset, counter, counter + area);
                workers[order] = new DownSamplingWorker(splice, area);


                threads[i] = new Thread(workers[order]);
                threads[i].start();

                counter+=area;
                order++;

            }

            for (Thread thread: threads) {
                try {
                    thread.join();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }

        double[][] processedData = new double[workers.length][workers[0].getNewData().length];
        for (int i = 0; i < workers.length; i++) {
            processedData[i] = workers[i].getNewData();
        }
        // DOWNSAMPLE END

        //search path:
        int dStartX = (int) Math.ceil(startX/(dataset[0].length / processedData[0].length));
        int dStartY = (int) Math.ceil(startY/(dataset.length / processedData.length));
        int dEndX = (int) Math.ceil(endX/(dataset[0].length / processedData[0].length));
        int dEndY = (int) Math.ceil(endY/(dataset.length / processedData.length));


        // This has the whole path we're looking for stored in it in the form of parents.
        PathFinder.Point Path = new PathFinder().pathFinder(dStartX, dStartY, dEndX, dEndY, processedData, area);

        List<PathFinder.Point> PathPoints = new ArrayList<>();
        while (Path != null) {
            PathPoints.add(Path);
            Path = Path.Parent;
        }

        // PathPoints now contains all the Points we need to go through for optimal travel

        List<Double> UpdatedPoints = new ArrayList<>();
        for (PathFinder.Point p: PathPoints) {
            p.X = p.X * (dataset[0].length / processedData[0].length);
            p.Y = p.Y * (dataset.length / processedData.length);
            UpdatedPoints.add((double)p.X);
            UpdatedPoints.add((double)p.Y);
        }
        return UpdatedPoints;
    }

}
