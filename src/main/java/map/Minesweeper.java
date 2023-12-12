package map;

import java.util.ArrayList;
import java.util.List;

import utils.Subject;

public class Minesweeper extends Subject {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 25;
    private static final int BOMB_CHANGE = 10;

    protected int width;
    protected int height;

    protected Cell[][] cells;
    protected int currentRound;

    private List<Vector2> marks;

    public Minesweeper(int width, int height){
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        this.currentRound = 0;
        this.marks = new ArrayList<>();
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

    public boolean isValidCoordinate(Vector2 v){
        return isValidCoordinate(v.x, v.y);
    }

    public boolean isValidCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public Cell getCell(Vector2 v){
        return getCell(v.x, v.y);
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    public List<Vector2> getMarks(){
        return marks;
    }

    private static CellType randomCellType(){
        if(randomInt() < BOMB_CHANGE) return CellType.BOMB;
        return CellType.EMPTY;
    }

    private static int randomInt(){
        return (int)(Math.random() * (100 + 1));
    }

    public void placeMark(Vector2 v){
        Cell c = getCell(v);
        if(c != null && !c.isExplored()){
            if(!isMarked(v)) marks.add(v);
            else removeMark(v);
        }
    }

    public boolean isMarked(Vector2 v){
        return marks.contains(v);
    }

    public boolean removeMark(Vector2 v){
        return marks.remove(v);
    }

    public void explore(Vector2 v){
        if(!isValidCoordinate(v)) return;
        Cell c = getCell(v);
        if(isMarked(v)) removeMark(v);
        if(c.isNoBombAround()) exploreEmpty(v);
        else exploreCell(v);
        ++currentRound;
        notifyObservers();
    }

    public void exploreEmpty(int x, int y){
        exploreEmpty(new Vector2(x, y));
    }

    public void exploreEmpty(Vector2 v){
        if(!isValidCoordinate(v)) return;
        Cell c = getCell(v);
        if(c.explored || !c.isNoBombAround()) return;
        exploreCell(v);
        exploreEmpty(v.x, v.y + 1);
        exploreEmpty(v.x + 1, v.y);
        exploreEmpty(v.x, v.y - 1);
        exploreEmpty(v.x - 1, v.y);
        exploreAround(v);
    }

    public void exploreAround(Vector2 v){
        exploreAround(v.x, v.y + 1);
        exploreAround(v.x + 1, v.y);
        exploreAround(v.x, v.y - 1);
        exploreAround(v.x - 1, v.y);
    }

    public void exploreAround(int x, int y){
        Vector2 v = new Vector2(x, y);
        if(!isValidCoordinate(v)) return;
        exploreCell(v);
    }

    public void exploreCell(Vector2 v){
        Cell c = getCell(v);
        if(c == null) return;
        c.explored = true;
        if(isMarked(v)) removeMark(v);
    }
}
