
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
    private KeyHandler keyHandler;
    private EllipseCommand ellipseCommand;
    private UndoManager undoManager;

    public EllipseButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Ellipse");
        
        this.undoManager = undoManager;
        addActionListener(this);

        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new EllipseButton.MouseHandler();
        keyHandler = new KeyHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        // Change cursor when button is clicked
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        // Start listening for mouseclicks on the drawing panel
        
        drawingPanel.addFocusListener(keyHandler);
        drawingPanel.requestFocusInWindow();
        drawingPanel.addKeyListener(keyHandler);
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
            drawingPanel.removeKeyListener(keyHandler);
            point1 = event.getPoint();

            //    System.out.println("MousePressed at " + point1);
            ellipseCommand = new EllipseCommand(View.mapPoint(event.getPoint()));
            ellipseCommand.setEllipsePoint(View.mapPoint(point1));
            undoManager.beginCommand(ellipseCommand);
            curX = point1.x;
            curY = point1.y;
            dragging = true;
        }//End of mousePressed

        @Override
        public void mouseReleased(MouseEvent event) {
            point2 = event.getPoint();
            dragging = false;
            
            ellipseCommand.setEllipsePoint(View.mapPoint(point2));
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            undoManager.endCommand(ellipseCommand);

            drawingPanel.removeMouseListener(this);
            drawingPanel.removeMouseMotionListener(this);

        }//End of mouseReleased

        @Override
        public void mouseDragged(MouseEvent event) {
            if (!dragging) {
                return;
            }

            point2 = event.getPoint();

            ellipseCommand.setEllipsePoint(View.mapPoint(point2));

            view.repaint();

        }//End of mouseDragged

        @Override
        public void mouseMoved(MouseEvent event) {
        }
        
    }//End of Mouse Handler
    private class KeyHandler extends KeyAdapter implements FocusListener {
        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                drawingPanel.removeMouseListener(mouseHandler);
                drawingPanel.removeKeyListener(keyHandler);
                view.refresh();
            } 
        }
        
        @Override
        public void focusLost(FocusEvent event) {
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            drawingPanel.removeMouseListener(mouseHandler);
            //undoManager.endCommand(labelCommand);
            drawingPanel.removeKeyListener(keyHandler);
            //undoManager.endCommand(labelCommand);
            view.refresh();
        }
        
        @Override
        public void focusGained(FocusEvent event) {
            drawingPanel.addKeyListener(this);
        }
    }
}
