package view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import map.Minesweeper;
import utils.Observer;
import utils.Subject;

public class GameView extends View implements Observer {
    private static final int WIDTH = (int)(SCREEN_WIDTH * (2.0/3.0));
    private static final int HEIGHT = (int)(SCREEN_HEIGHT * (2.0/3.0));
    private static final String TITLE = "Personal - Minesweeper";

    private Minesweeper minesweeper;

    public GameView(Minesweeper gameScene){
        this.minesweeper = gameScene;
        gameScene.attach(this);
        init(WIDTH, HEIGHT);
    }

    @Override
    public String title() {
        return TITLE + " (" + minesweeper.getWidth() + "x" + minesweeper.getHeight() + ")";
    }

    @Override
    protected void view() {
        add(new GameCanvas(minesweeper));
    }

    @Override
    protected Point position() {
        return center();
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
