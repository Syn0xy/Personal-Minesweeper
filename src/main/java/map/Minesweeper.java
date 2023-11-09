package map;

import utils.Subject;

public class Minesweeper extends Subject {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static final int BOMB_CHANGE = 20;

    protected int width;
    protected int height;

    protected Cell[][] cells;
    protected int currentRound;

    public Minesweeper(int width, int height){
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        this.currentRound = 0;
        init();
    }

    public Minesweeper(){
        this(WIDTH, HEIGHT);
    }

    private void init(){
        for(int y = 0; y < cells.length; y++){
            for (int x = 0; x < cells[y].length; x++) {
                cells[y][x] = new Cell(randomCellType());
            }
        }
        for(int x = 0; x < cells.length; x++){
            for (int y = 0; y < cells[x].length; y++) {
                int bombAround = 0;
                for (int oy = -1; oy <= 1; oy++) {
                    for (int ox = -1; ox <= 1; ox++) {
                        if(oy == 0 && ox == 0) continue;
                        if(isValidCoordinate(x + ox, y + oy) && cells[x + ox][y + oy].isBomb())
                            bombAround++;
                    }
                }
                cells[x][y].bombAround = bombAround;
            }
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getCurrentRound(){
        return currentRound;
    }

    public boolean isValidCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    private static CellType randomCellType(){
        if(randomInt() < BOMB_CHANGE) return CellType.BOMB;
        return CellType.EMPTY;
    }

    private static int randomInt(){
        return (int)(Math.random() * (100 + 1));
    }

    public void explore(int x, int y){
        if(!isValidCoordinate(x, y)) return;
        Cell c = cells[x][y];
        if(c.isNoBombAround()) exploreEmpty(x, y);
        else c.explored = true;
        ++currentRound;
        notifyObservers();
    }

    public void exploreEmpty(int x, int y){
        if(!isValidCoordinate(x, y)) return;
        Cell c = cells[x][y];
        if(c.explored || !c.isNoBombAround()) return;
        c.explored = true;
        exploreEmpty(x, y + 1);
        exploreEmpty(x + 1, y);
        exploreEmpty(x, y - 1);
        exploreEmpty(x - 1, y);
    }
}
