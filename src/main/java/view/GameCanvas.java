package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import map.Cell;
import map.Minesweeper;
import map.Vector2;

public class GameCanvas extends JPanel {
    private static final String SP = File.separator;
    private static final String CELL_EMPTY_IMAGE_FILENAME = "." + SP + "res" + SP + "empty.png";
    private static final String CELL_UNEXPLORED_IMAGE_FILENAME = "." + SP + "res" + SP + "cell.png";
    private static final String CELL_BOMB_IMAGE_FILENAME = "." + SP + "res" + SP + "mine.png";

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color SELECTED_COLOR = Color.WHITE;
    private static final Color MARKED_COLOR = Color.CYAN;
    private static final Color NUMBER_COLOR = Color.BLACK;
    private static final int CELL_SIZE = 32;

    private static final int LEFT_MOUSE_CLICK = 1;
    private static final int RIGHT_MOUSE_CLICK = 3;

    private Minesweeper minesweeper;

    private BufferedImage cellEmptyImage;
    private BufferedImage cellUnexploredImage;
    private BufferedImage cellBombImage;
    private Vector2 selected;

    public GameCanvas(Minesweeper minesweeper){
        this.minesweeper = minesweeper;
        this.selected = new Vector2();
        init();
    }

    public void init(){
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == LEFT_MOUSE_CLICK)
                    minesweeper.explore(selected);
                else if(e.getButton() == RIGHT_MOUSE_CLICK)
                    minesweeper.placeMark(selected.copy());
                repaint();
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                selected.x = e.getX() / CELL_SIZE;
                selected.y = e.getY() / CELL_SIZE;
                repaint();
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        try {
            cellEmptyImage = ImageIO.read(new File(CELL_EMPTY_IMAGE_FILENAME));
            cellUnexploredImage = ImageIO.read(new File(CELL_UNEXPLORED_IMAGE_FILENAME));
            cellBombImage = ImageIO.read(new File(CELL_BOMB_IMAGE_FILENAME));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        clearScreen(g);
        draw(g);
    }

    public void clearScreen(Graphics g){
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void draw(Graphics g){
        for (int y = 0; y < minesweeper.getHeight(); y++) {
            for (int x = 0; x < minesweeper.getWidth(); x++) {
                drawCell(g, x, y);
            }
        }
        drawSelected(g);
        drawMarks(g);
    }
    
    private void drawCell(Graphics g, int x, int y){
        Cell c = minesweeper.getCell(x, y);
        int px = x * CELL_SIZE;
        int py = y * CELL_SIZE;
        drawCell(g, c, px, py);
        if(c.isExplored() && !c.isBomb() && !c.isNoBombAround()) drawCellNumber(g, c, px, py);
    }

    private void drawCell(Graphics g, Cell c, int x, int y){
        BufferedImage image = null;
        switch (c.getType()) {
            case EMPTY:
                if(c.isExplored()) image = cellEmptyImage;
                else image = cellUnexploredImage;
                break;
            case BOMB: image = cellBombImage; break;
        }
        g.drawImage(image, x, y, CELL_SIZE, CELL_SIZE, null);
    }
    
    private void drawSelected(Graphics g){
        if(!minesweeper.isValidCoordinate(selected)) return;
        Cell c = minesweeper.getCell(selected);
        if(c.isExplored()) return;
        int px = selected.x * CELL_SIZE;
        int py = selected.y * CELL_SIZE;
        g.setColor(SELECTED_COLOR);
        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
    }

    private void drawMarks(Graphics g){
        g.setColor(MARKED_COLOR);
        for (Vector2 v : minesweeper.getMarks()) {
            int px = v.x * CELL_SIZE + CELL_SIZE / 4;
            int py = v.y * CELL_SIZE + CELL_SIZE / 4;
            int size = CELL_SIZE - CELL_SIZE / 2;
            g.fillRect(px, py, size, size);
        }
    }
    
    private void drawCellNumber(Graphics g, Cell c, int px, int py){
        g.setColor(NUMBER_COLOR);
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
}
