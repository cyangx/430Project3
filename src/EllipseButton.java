
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author edorphy
 */
public class EllipseButton extends JButton implements ActionListener{
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
   /* @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    
   public void actionPerformed(ActionEvent event) {
    view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    // Change cursor when button is clicked
    drawingPanel.addMouseListener(mouseHandler);
    // Start listening for mouseclicks on the drawing panel
   }//End of actionPerformed
   
   
    private class MouseHandler extends MouseAdapter {
      private int pointCount = 0;  
      public void mouseClicked(MouseEvent event) {
      if (++pointCount == 1) {
        ellipseCommand = new EllipseCommand(View.mapPoint(event.getPoint()));
        undoManager.beginCommand(ellipseCommand);
    } else if (pointCount == 2) {
        pointCount = 0;
        ellipseCommand.setEllipsePoint(View.mapPoint(event.getPoint()));
        drawingPanel.removeMouseListener(this);
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        undoManager.endCommand(ellipseCommand);
      }
    }
  }//ENd of Mouse Handler
}
