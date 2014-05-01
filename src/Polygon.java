
import java.awt.*;
import java.util.*;
/*
 * @author Cha Yang
 */

public class Polygon extends Item {

    private Stack points;
    private Point firstPoint;
    private Point prevPoint;
    private Point currentPoint;
    private boolean polyDone;

    public Polygon(Point point1) {
        this();
        setFirstPoint(point1);
        prevPoint = point1;
        points.push(point1);
        polyDone = false;
    }

    public Polygon() {
        points = new Stack();
    }

    @Override
    public boolean includes(Point point) {
        //return ((distance(point, firstPoint) < 10.0) || (distance(point, prevPoint) < 10.0));
        Point p;
        for (int i = 0; i < this.points.size(); i++){
            p = (Point) points.get(i);
            if (distance(point, p) < 10.0){
                return true;
            }
        } 
        return false;       
    }

    @Override
    public void render() {
        uiContext.draw(this);
    }

    public void setFirstPoint(Point point) {
        firstPoint = point;
    }

    public void done() {
        polyDone = true;
    }

    public boolean isdone() {
        return polyDone;
    }

    public void setNextPoint(Point point) {
        prevPoint = currentPoint;
        //System.out.println("prevPoint:" + prevPoint);
        points.push(point);
    }

    public Stack getPolyPoints() {
        return points;
    }

    public void setCurrentPoint(Point point) {
        currentPoint = point;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getprevPoint() {
        return prevPoint;
    }

    @Override
    public String toString() {
        return "Polygon  from " + firstPoint + " to " + prevPoint;
    }

    @Override
    public void move(int x, int y) {
        
        Stack temp = new Stack();
        while (!points.isEmpty()) {
            temp.push(points.pop());            
        }
        
        while (!temp.isEmpty()) {
            Point tempPt = (Point) temp.pop();
            tempPt.x += x;
            tempPt.y += y;
            points.push(tempPt);
        }
    }
}
