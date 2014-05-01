
import java.awt.*;
import java.io.*;

public abstract class Item implements Serializable {

    protected static UIContext uiContext;
    protected boolean selection = false;

    public static void setUIContext(UIContext uiContext) {
        Item.uiContext = uiContext;
    }

    public abstract boolean includes(Point point);
    public abstract void move(int x, int y);
    ////
    protected void setSelection(boolean selection){
        this.selection = selection;
    }
    
    protected boolean selection(){
        return selection;
    }
    /////
            
    protected double distance(Point point1, Point point2) {
        double xDifference = point1.getX() - point2.getX();
        double yDifference = point1.getY() - point2.getY();
        return ((double) (Math.sqrt(xDifference * xDifference + yDifference * yDifference)));
    }

    public void render() {
        uiContext.draw(this);
    }
}
