import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/*
 * @author Cha Yang
 */

public class PolygonButton extends JButton implements ActionListener {

    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private PolygonCommand polyCommand;
    private UndoManager undoManager;

    public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter implements MouseMotionListener {

        private int pointCount = 0;

        @Override
        public void mouseClicked(MouseEvent event) {
            if (++pointCount == 1) {
                polyCommand = new PolygonCommand(View.mapPoint(event.getPoint()));
                undoManager.beginCommand(polyCommand);
                drawingPanel.addMouseMotionListener(mouseHandler);
            } else if (pointCount > 1) {
                Polygon poly = polyCommand.getPoly();
                pointCount++;
                polyCommand.setPolyPoint(View.mapPoint(event.getPoint()));
                if (poly.isdone()) {
                    drawingPanel.removeMouseListener(this);
                    drawingPanel.removeMouseMotionListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    undoManager.endCommand(polyCommand);
                    polyCommand = null;
                    pointCount = 0;
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            Point point;
            point = View.mapPoint(event.getPoint());
            polyCommand.getPoly().setCurrentPoint(point);
            undoManager.refresh();
        }

    }
}
