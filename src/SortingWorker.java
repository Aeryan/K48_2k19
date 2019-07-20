import java.util.HashMap;
import java.util.List;

public class SortingWorker implements Runnable {

    public boolean HasFound = true;
    final private int Start;
    final private int End;
    final private HashMap<Integer, PathFinder.Point> List;
    final private PathFinder.Point Point;
    public double F;

    public SortingWorker(HashMap<Integer, PathFinder.Point> points, PathFinder.Point point, int start, int end) {
        this.List = points;
        this.Start = start;
        this.End = end;
        this.Point = point;
    }

    public void run() {
        for (int i = Start; (i < End) && (i < List.size()); i++) {
            PathFinder.Point op = (PathFinder.Point)(List.values()).toArray()[i];
            if (op.X == Point.X && op.Y == Point.Y) {
                if (op.F < Point.F) {
                    HasFound = true;
                    F = op.F;
                    return;
                }
            }
        }
        HasFound = false;
    }

}
