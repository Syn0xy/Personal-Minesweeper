package view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import utils.Observer;

public abstract class CanvasView extends Canvas implements Observer {
    protected static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    protected static final int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();
    protected static final int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();

    public CanvasView(int width, int height){
        Frame f = new Frame(title());
        f.setSize(width, height);
        f.add(this);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
        f.setLocation(position());
    }

    public abstract String title();
    public abstract Point position();
    
    public void update(Graphics g) {
        paint(g);
    }

    public abstract void paint(Graphics g);

    public Point center(){
        return new Point((SCREEN_WIDTH - getWidth()) / 2, (SCREEN_HEIGHT - getHeight()) / 2);
    }
}
