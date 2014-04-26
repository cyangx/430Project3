
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Stack;

public class NewSwingUI implements UIContext {

    private Graphics graphics;
    private static NewSwingUI swingUI;

    private NewSwingUI() {
    }

    public static NewSwingUI getInstance() {
        if (swingUI == null) {
            swingUI = new NewSwingUI();
        }
        return swingUI;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void draw(Label label) {
        if (label.getStartingPoint() != null) {
            if (label.getText() != null) {
                graphics.drawString(label.getText(), (int) label.getStartingPoint().getX(), (int) label.getStartingPoint().getY());
            }
        }
        int length = graphics.getFontMetrics().stringWidth(label.getText());
        graphics.drawString("_", (int) label.getStartingPoint().getX() + length, (int) label.getStartingPoint().getY());
    }

    @Override
    public void draw(Line line) {
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        if (line.getPoint1() != null) {
            i1 = Math.round((float) (line.getPoint1().getX()));
            i2 = Math.round((float) (line.getPoint1().getY()));
            if (line.getPoint2() != null) {
                i3 = Math.round((float) (line.getPoint2().getX()));
                i4 = Math.round((float) (line.getPoint2().getY()));
            } else {
                i3 = i1;
                i4 = i2;
            }
            graphics.drawLine(i1, i2, i3, i4);
        }
    }

    @Override
    public void draw(Ellipse ellipse) {
        Graphics2D graphics2d = (Graphics2D) graphics;

        int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
        

        //Working normal code     
        if (ellipse.getPoint1() != null) {

            i1 = Math.round((float) (ellipse.getPoint1().getX()));
            i2 = Math.round((float) (ellipse.getPoint1().getY()));
            if (ellipse.getPoint2() != null) {
                i3 = Math.round((float) (ellipse.getPoint2().getX()));
                i4 = Math.round((float) (ellipse.getPoint2().getY()));

                if (i3 >= i1 && i4 >= i2) {// Top left to bottom right
                    graphics2d.draw(new Ellipse2D.Double(i1, i2, i3 - i1, i4 - i2));
                    //graphics.drawLine(i1, i2, i1, i4);          //Tinker (Debug)
                } else if (i1 > i3 && i2 > i4) {                  //Bottom right to top left
                    graphics2d.draw(new Ellipse2D.Double(i3, i4, i1 - i3, i2 - i4));

                } //else if bottom left to top right
                else if (i1 <= i3 && i2 >= i4) {
                    //graphics.drawLine(i1, i2, i1, i4);   
                    graphics2d.draw(new Ellipse2D.Double(i1, i4, i3 - i1, i2 - i4));
                }//else if top right to bottom left
                else if (i1 >= i3 && i2 <= i4) { //if (i1 < i3 && i2 < i4){
                    //graphics.drawLine(i1, i2, i3, i2);   
                    graphics2d.draw(new Ellipse2D.Double(i3, i2, i1 - i3, i4 - i2));
                }
                //else System.out.println("Error?");

            } else {
                i3 = i1;
                i4 = i2;
                graphics.drawLine(i1, i2, i3, i4);           //Draws single point
            }
        }
    }//End of draw ellipse


    /*
     * @author Cha Yang
     */
    @Override
    public void draw(Polygon poly) {
        Stack stack = (Stack) poly.getPolyPoints().clone();
        Point point = null;
        Point point2 = null;
        int x;
        int y;
        int x2;
        int y2;

        if (!poly.isdone()) {
            // draw a square around the first point
            graphics.drawRect(poly.getFirstPoint().x - 5, poly.getFirstPoint().y - 5, 10, 10);
            // draw the red line
            try {
                x = poly.getCurrentPoint().x;
                y = poly.getCurrentPoint().y;
                x2 = poly.getprevPoint().x;
                y2 = poly.getprevPoint().y;
                graphics.setColor(Color.red);
                graphics.drawLine(x, y, x2, y2);
                graphics.setColor(Color.blue);
            } catch (Exception e) {
            }

        }

        while (!stack.isEmpty()) {
            if (point2 != null) {
                point = point2;
            }
            if (!stack.isEmpty()) {
                point2 = (Point) stack.pop();
            }
            if (point != null) {
                x = Math.round((float) (point.getX()));
                y = Math.round((float) (point.getY()));
                if (point2 != null) {
                    x2 = Math.round((float) (point2.getX()));
                    y2 = Math.round((float) (point2.getY()));
                } else {
                    x2 = x;
                    y2 = y;
                }
                graphics.drawLine(x, y, x2, y2);
            }
        }
    }

    @Override
    public void draw(Item item) {
        System.out.println("Cant draw unknown Item \n");
    }
}
