
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author edorphy
 */
public class EllipseButton extends JButton implements ActionListener {

    protected JPanel drawingPanel;
    protected View view;
    private EllipseButton.MouseHandler mouseHandler;
    private EllipseCommand ellipseCommand;
    private UndoManager undoManager;

    public EllipseButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Ellipse");

        this.undoManager = undoManager;
        addActionListener(this);

        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new EllipseButton.MouseHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        // Change cursor when button is clicked
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        // Start listening for mouseclicks on the drawing panel
    }//End of actionPerformed

    private class MouseHandler extends MouseAdapter implements MouseListener, MouseMotionListener {

        int sX = -1, sY = -1;
        int curX = -1, curY = -1;
        boolean dragging = false;
        Point point1, point2;

        @Override
        public void mouseClicked(MouseEvent event) {

        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            point1 = event.getPoint();

            //    System.out.println("MousePressed at " + point1);
            ellipseCommand = new EllipseCommand(View.mapPoint(event.getPoint()));
            undoManager.beginCommand(ellipseCommand);
            curX = point1.x;
            curY = point1.y;
            dragging = true;
        }//End of mousePressed

        @Override
        public void mouseReleased(MouseEvent event) {
            point2 = event.getPoint();
            dragging = false;
            ellipseCommand.setEllipsePoint(View.mapPoint(point1));
            ellipseCommand.setEllipsePoint(View.mapPoint(point2));
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            undoManager.endCommand(ellipseCommand);
            //System.out.println("Mouse released at: " + point2);
            //System.out.println("Drawn ellipse area is : " + sX + "," + sY + " to " + curX + "," + curY);
            drawingPanel.removeMouseListener(this);
            drawingPanel.removeMouseMotionListener(this);

        }//End of mouseReleased

        @Override
        public void mouseDragged(MouseEvent event) {
            if (!dragging) {
                return;
            }

            point2 = event.getPoint();

            ellipseCommand.setEllipsePoint(View.mapPoint(point1));
            ellipseCommand.setEllipsePoint(View.mapPoint(point2));

            view.repaint();

            //Debuggery purposes
            /*curX = point.x;
             curY = point.y;
             System.out.println("Dragging, x = " + curX + " y = " + curY);
             //System.out.println("Dragging, x = " + curX + " y = " + curY);
             if(dragging){
             System.out.println("Dragging, x = " + curX + " y = " + curY);
             view.refresh();
             view.repaint();
             repaint();
             }*/
        }//End of mouseDragged

        @Override
        public void mouseMoved(MouseEvent event) {
        }

        /*//Working code
         private int pointCount = 0;  
         public void mouseClicked(MouseEvent event) {
         if (++pointCount == 1) {
         ellipseCommand = new EllipseCommand(View.mapPoint(event.getPoint()));
         System.out.println("event.getPoint: " + event.getPoint());
         undoManager.beginCommand(ellipseCommand);
         } else if (pointCount == 2) {
         pointCount = 0;
         ellipseCommand.setEllipsePoint(View.mapPoint(event.getPoint()));
         System.out.println("event.getPoint: " + event.getPoint());
         drawingPanel.removeMouseListener(this);
         view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         undoManager.endCommand(ellipseCommand);
         }
         }*/
    }//ENd of Mouse Handler
}
