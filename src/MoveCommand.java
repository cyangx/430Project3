
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
public class MoveCommand extends Command {

    private int pointCount;
    
    private int x = 0, y = 0;
    private Point point1, point2;
    
    public MoveCommand() {
        point1 = new Point(0, 0);
        point2 = new Point(0, 0);
        //model.moveSelectedItems(5, 5);
    }
   
    
    public void setPoint1(Point point) {
        point1 = point;
    }
    
    public void setPoint2(Point point) {
        point2 = point;
        x = point2.x - point1.x;
        y = point2.y - point1.y;
        model.moveSelectedItems(x, y); 
    }
    
    @Override
    public boolean undo() {
        model.moveSelectedItems(-x, -y);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public void execute() {
         // Do nothing in here 
        model.moveSelectedItems(x, y);
    }

}
