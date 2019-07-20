import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PathFinder {

    public class Point {
        final public int X;
        final public int Y;
        public double F;
        public double G;
        public Point Parent;
        public final int Hash;

        public Point(int x, int y) {
            this.X = x;
            this.Y = y;
            this.Hash = Integer.parseInt(Integer.toString(X) + Integer.toString(Y));
        }

        public double dist(double x1, double y1) {
            return Math.sqrt(Math.pow(x1 - X, 2) + Math.pow(y1 - Y, 2));
        }
    }

    public boolean checkExists(HashMap<Integer, Point> points, Point p) {

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];
        int size = (int)Math.ceil(points.size() / cores);
        List<SortingWorker> workers = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < cores; i++) {
            SortingWorker worker = new SortingWorker(points, p, c, c+size);
            workers.add(worker);
            threads[i] = new Thread(worker);
            threads[i].start();
            c += size;
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        for (SortingWorker worker: workers) {
            if (!worker.HasFound) {
                return worker.HasFound;
            }
        }
        return true;
    }

    public Point pathFinder(int startX, int startY, int endX, int endY, double[][] dataset, int area) {

        HashMap<Integer, Point> openList = new HashMap<>();
        HashMap<Integer, Point> closedList = new HashMap<>();

        // Generate openList
        Point s = new Point(startX, startY);
        s.F = 0;
        s.G = 0;
        openList.put(s.Hash, s);
        //

        Point sel = s;

        while (openList.size() > 0) {
            System.out.println(openList.size());
            // FIND THE POINT WITH LOWEST HURDLE
            for (Point p: openList.values()) {
                if (sel.F > p.F) {
                    sel = p;
                }
            }
            closedList.put(sel.Hash, sel);
            openList.remove(sel.Hash);
            //

            // Generate surrounding points! + add them to open list if they meet the specifications
            for (int x = sel.X - 1; x < sel.X + 2; x++) {
                if (x > 0 && x < dataset[0].length) {
                    for (int y = sel.Y - 1; y < sel.Y + 2; y++) {
                        if (y > 0 && y < dataset.length && (sel.X != x && sel.Y != y)) {

                            if (x == endX && y == endY) {
                                closedList.put(sel.Hash, sel);
                                System.out.println("FOUND!");
                                Point p = new Point(x, y);
                                p.Parent = sel;
                                return p;
                            }

                            Point p = new Point(x, y);
                            p.Parent = sel;
                            p.G = sel.G + dataset[y][x] * area;
                            p.F = p.G + p.dist(endX, endY);

                            //Check for same points, but worse hurdle
                            boolean add = checkExists(openList, p);
                            if (add) {
                                add = checkExists(closedList, p);
                            }
                            //
                            if (add) {
                                openList.put(p.Hash, p);
                            }
                        }
                    }
                }
            }
            //

            // WHEN WE REACH HERE, WE HAVE EITHER FOUND A PATH OR THERE'S NO WAY TO REACH IT
        }
        return sel;
    }

}
