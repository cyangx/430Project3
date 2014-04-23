
import java.awt.Point;

/**
 *
 * @author edorphy
 */
public class Ellipse extends Item {

    private Point point1;
    private Point point2;
    
    
    public Ellipse(Point point1, Point point2) {        
        this.point1 = point1;
        this.point2 = point2;        
    }

    public Ellipse(Point point1) {
        this.point1 = point1;
    }

    public Ellipse() {
    }

    @Override
    public boolean includes(Point point) {
        return ((distance(point, point1) < 10.0) || (distance(point, point2) < 10.0));
    }

    @Override
    public void render() {
        uiContext.draw(this);
    }

    public void setPoint1(Point point) {
        point1 = point;
    }

    public void setPoint2(Point point) {
        point2 = point;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    @Override
    public String toString() {
        return "Ellipse  from " + point1 + " to " + point2;
    }

    @Override
    public void move(int x, int y) {
        point1.x += x;
        point1.y += y;
        
        point2.x += x;
        point2.y += y;
    }

}
