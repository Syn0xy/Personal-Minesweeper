package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import map.Cell;
import map.CellType;
import map.Minesweeper;
import utils.Subject;

public class MinesweeperView extends CanvasView {
    private static final int WIDTH = (int)(SCREEN_WIDTH * (2.0 / 3.0));
    private static final int HEIGHT = (int)(SCREEN_HEIGHT * (2.0 / 3.0));
    private static final String TITLE = "Demineur";
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color UNEXPLORED_COLOR = Color.GRAY;
    private static final Color BOMB_COLOR = Color.RED;
    private static final Color EMPTY_COLOR = Color.BLUE;
    private static final int CELL_SIZE = 16;

    private Minesweeper minesweeper;

    public MinesweeperView(Minesweeper map, int width, int height){
        super(width, height);
        this.minesweeper = map;
        this.minesweeper.attach(this);
        repaint();
    }

    public MinesweeperView(Minesweeper map){
        this(map, WIDTH, HEIGHT);
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    public Point position() {
        return center();
    }

    @Override
    public void paint(Graphics g) {
        if(minesweeper == null) return;
        setBackground(BACKGROUND_COLOR);
        draw(g);
    }

    private void draw(Graphics g){
        for (int y = 0; y < minesweeper.getHeight(); y++) {
            for (int x = 0; x < minesweeper.getWidth(); x++) {
                drawCell(g, x, y);
            }
        }
    }

    private void drawCell(Graphics g, int x, int y){
        Cell c = minesweeper.getCell(x, y);
        g.setColor(getCellColor(c));
        int px = x * CELL_SIZE;
        int py = y * CELL_SIZE;
        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
        if(c.isExplored() && !c.isBomb() && !c.isNoBombAround()) drawCellNumber(g, c, px, py);
    }

    private void drawCellNumber(Graphics g, Cell c, int px, int py){
        g.setColor(Color.WHITE);
        drawCenteredString(g, String.valueOf(c.bombAround()), px + CELL_SIZE / 2, py + CELL_SIZE / 2);
    }
    
    public void drawCenteredString(Graphics g, String s, int x, int y) {
        Font f = g.getFont();
        FontMetrics metrics = g.getFontMetrics(f);
        int size = f.getSize();
        x = x + (size - metrics.stringWidth(s)) / 2;
        y = y + ((size - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(s, x - size / 2, y - size / 2);
    }

    private Color getCellColor(Cell c){
        if(c.isExplored()) return getCellTypeColor(c.getType());
        return UNEXPLORED_COLOR;
    }

    private Color getCellTypeColor(CellType type){
        switch (type) {
            case EMPTY: return EMPTY_COLOR;
            case BOMB: return BOMB_COLOR;
        }
        return null;
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
