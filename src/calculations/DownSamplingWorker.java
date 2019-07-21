package calculations;

public class DownSamplingWorker implements Runnable {

    final private double[][] GivenData;
    volatile private double[] NewData;
    final private int Area;

    public DownSamplingWorker(double[][] givenData, int area) {
        this.GivenData = givenData;
        this.NewData = new double[givenData[0].length/area];
        this.Area = area;
    }

    public double[] getNewData() {
        return NewData;
    }

    public void run() {
        int xLength = GivenData[0].length;
        System.out.println(GivenData.length);
        double[] tempData = new double[xLength];
        for (int y = 0; y < GivenData.length; y+=Area) {
            for (int i = 0; i < Area; i++) {
                for (int x = 0; x < xLength; x++) {
                    tempData[x] = tempData[x] + GivenData[y+i][x];
                }
            }
        }
        double MAX = 0;
        for (int x = 0; x < tempData.length; x+=Area) {
            for (int i = 0; i < Area; i++) {
                NewData[x/Area] = NewData[x/Area] + tempData[x+i];
            }
            if (MAX < NewData[x/Area]) {
                MAX = NewData[x/Area];
            }
        }
        for (int i = 0; i < NewData.length; i++) {
            NewData[i] = NewData[i] / MAX;
        }
    }

}
