
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author edorphy
 */
public class MoveButton extends JButton implements ActionListener {

    private final UndoManager undoManager;  
    private MouseHandler mouseHandler;
    private MoveCommand moveCommand;
    private JPanel drawingPanel;
    
    public MoveButton(UndoManager undoManager, JPanel drawingPanel) {
        super("Move");
        this.undoManager = undoManager;
        this.drawingPanel = drawingPanel;
        addActionListener(this);
        mouseHandler = new MouseHandler();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //moveCommand = new MoveCommand();
        
        drawingPanel.addMouseListener(mouseHandler);
        //undoManager.beginCommand(moveCommand);
        //undoManager.endCommand(moveCommand);
    }
    
    private class MouseHandler extends MouseAdapter {
        
        private int pointCount = 0;
        
        @Override
        public void mouseClicked(MouseEvent event) {
            if (++pointCount == 1) {
                moveCommand = new MoveCommand();
                moveCommand.setPoint1(View.mapPoint(event.getPoint()));
                undoManager.beginCommand(moveCommand);
            } else if (pointCount == 2) {
                moveCommand.setPoint2(View.mapPoint(event.getPoint()));                
                drawingPanel.removeMouseListener(this);
                pointCount = 0;
                undoManager.endCommand(moveCommand);
            }
        }
    }

}
