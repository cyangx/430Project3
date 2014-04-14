
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edorphy
 */
public class Ellipse extends Item{
/*
    @Override
    public boolean includes(Point point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
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
    public boolean includes(Point point) {
        return ((distance(point, point1 ) < 10.0) || (distance(point, point2)< 10.0));
    }
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
    public String toString() {
        return "Ellipse  from " + point1 + " to " + point2;
    }
    
}
