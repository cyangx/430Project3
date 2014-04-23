
import java.awt.Point;

/**
 *
 * @author Brandon Theisen
 */
public class EllipseCommand extends Command {

    private Ellipse ellipse;
    private int pointCount;

    public EllipseCommand() {
        this(null, null);
        pointCount = 0;
    }

    public EllipseCommand(Point point) {
        this(point, null);
        //pointCount = 1;
    }

    public EllipseCommand(Point point1, Point point2) {
        ellipse = new Ellipse(point1, point2);
        //pointCount = 2;
    }

    public void setEllipsePoint(Point point) {
        //System.out.println("pointCount = " + pointCount);
        if (pointCount == 0) {
            //    System.out.println("setEllipsePoint if");
            pointCount++;
            ellipse.setPoint1(point);

        } else if (pointCount == 1) {
            //    System.out.println("setEllipsePoint else");
            ellipse.setPoint2(point);
            //    System.out.println(ellipse.toString());

        }

    }

    @Override
    public void execute() {
        model.addItem(ellipse);
    }

    @Override
    public boolean undo() {
        model.removeItem(ellipse);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (ellipse.getPoint1() == null) {
            undo();
            return false;
        }
        if (ellipse.getPoint2() == null) {
            ellipse.setPoint2(ellipse.getPoint1());
        }
        return true;
    }
}
