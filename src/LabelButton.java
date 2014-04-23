
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class LabelButton extends JButton implements ActionListener {

    protected JPanel drawingPanel;
    protected View view;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private LabelCommand labelCommand;
    private UndoManager undoManager;

    public LabelButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Label");
        this.undoManager = undoManager;
        keyHandler = new KeyHandler();
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        keyHandler = new KeyHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        drawingPanel.addMouseListener(mouseHandler = new MouseHandler());
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            view.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            if (labelCommand != null) {
                undoManager.endCommand(labelCommand);
            }
            labelCommand = new LabelCommand(View.mapPoint(event.getPoint()));
            undoManager.beginCommand(labelCommand);
            drawingPanel.addFocusListener(keyHandler);
            drawingPanel.requestFocusInWindow();
            drawingPanel.addKeyListener(keyHandler);
        }
    }

    private class KeyHandler extends KeyAdapter implements FocusListener {

        @Override
        public void keyTyped(KeyEvent event) {
            char character = event.getKeyChar();
            if (character >= 32 && character <= 126) {
                labelCommand.addCharacter(event.getKeyChar());
            }
        }

        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                drawingPanel.removeMouseListener(mouseHandler);
                drawingPanel.removeKeyListener(keyHandler);
                undoManager.endCommand(labelCommand);
                view.refresh();
            } else if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                labelCommand.removeCharacter();
            }
        }

        @Override
        public void focusGained(FocusEvent event) {
            drawingPanel.addKeyListener(this);
        }

        @Override
        public void focusLost(FocusEvent event) {
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            drawingPanel.removeMouseListener(mouseHandler);
            undoManager.endCommand(labelCommand);
            drawingPanel.removeKeyListener(keyHandler);
            undoManager.endCommand(labelCommand);
            view.refresh();
        }
    }
}
