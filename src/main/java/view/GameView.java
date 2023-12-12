package view;

import java.awt.Point;

import map.Minesweeper;
import utils.Observer;
import utils.Subject;

public class GameView extends View implements Observer {
    private static final int WIDTH = (int)(SCREEN_WIDTH * (2.0 / 3.0));
    private static final int HEIGHT = (int)(SCREEN_HEIGHT * (2.0 / 3.0));
    private static final String TITLE = "Demineur";
    
    private Minesweeper minesweeper;
    
    public GameView(Minesweeper minesweeper, int width, int height){
        this.minesweeper = minesweeper;
        minesweeper.attach(this);
        init(width, height);
    }

    public GameView(Minesweeper minesweeper){
        this(minesweeper, WIDTH, HEIGHT);
    }

    @Override
    public String title() {
        return TITLE + " (" + minesweeper.getWidth() + "x" + minesweeper.getHeight() + ")";
    }

    @Override
    public Point position() {
        return center();
    }
    
    @Override
    public void view(){
        add(new GameCanvas(minesweeper));
    }

    @Override
    public void update(Subject subj) {
        repaint();
    }

    @Override
    public void update(Subject subj, Object data) {
        repaint();
    }

}