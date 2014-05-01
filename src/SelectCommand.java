
import java.awt.*;
import java.util.*;

public class SelectCommand extends Command {

    private Item item;
    private boolean commandEnd = false;

    public SelectCommand() {
        Enumeration enumeration = model.getItems();
        while (enumeration.hasMoreElements()) {
            item = (Item) (enumeration.nextElement());
            item.setSelection(true);
        }
        Enumeration enumeration1 = model.getSelectedItems();
        while (enumeration1.hasMoreElements()) {
            item = (Item) (enumeration1.nextElement());
            item.setSelection(true);
        }
    }

    public void endSelect(boolean cmd){
        this.commandEnd = cmd;
    }
    
    public void setPoint(Point point) {
        boolean flag = false;                       
        Enumeration enumeration1 = model.getSelectedItems();
        while (enumeration1.hasMoreElements()) {
            item = (Item) (enumeration1.nextElement());
            item.setSelection(false);
            if (item.includes(point)) {
                model.unSelect(item);       // unselect item if it was already selected
                flag = true;
                //break;
            }
        }
        Enumeration enumeration = model.getItems();
        while (enumeration.hasMoreElements()) {
            item = (Item) (enumeration.nextElement());
            item.setSelection(false);
            if (item.includes(point) && flag == false) { // check flag to make sure you dont select the point 
                                                         // again that you just unselected
                model.markSelected(item);       // select item 
                //break;
            }
        }
    }
    
    @Override
    public boolean undo() {
        model.unSelect(item);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    @Override
    public void execute() {
        model.setChanged();
    }
    
    @Override
    public boolean end() { 
        return this.commandEnd;
    }
}
