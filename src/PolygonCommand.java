
import java.awt.*;
/*
 * @author Cha Yang
 */

public class PolygonCommand extends Command {

    private Polygon poly;
    private int pointCount;

    public PolygonCommand() {
        this(null);
        pointCount = 0;
    }

    public PolygonCommand(Point point) {
        poly = new Polygon(point);
        pointCount++;
    }

    public void setPolyPoint(Point point) {
        // small square
        Rectangle _r;
        _r = new Rectangle(poly.getFirstPoint().x - 5, poly.getFirstPoint().y - 5, 10, 10);

        if (++pointCount == 1) {
            poly.setFirstPoint(point);
        } else if (pointCount > 1) {
            // if the new point is inside the small square
            if (_r.contains(point)) {
                poly.done();
                point = poly.getFirstPoint();
            }
            poly.setNextPoint(point);
        }
    }

    public Polygon getPoly() {
        return poly;
    }

    @Override
    public void execute() {
        model.addItem(poly);
    }

    @Override
    public boolean undo() {
        model.removeItem(poly);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public boolean end() {
        if (poly.getFirstPoint() == null) {
            undo();
            return false;
        }

        return true;
    }
}
