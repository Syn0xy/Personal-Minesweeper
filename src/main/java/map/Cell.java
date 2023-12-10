package map;

public class Cell {
    protected CellType type;
    protected int bombAround;
    protected boolean explored;

    public Cell(CellType type){
        this.type = type;
        this.explored = false;
    }

    public CellType getType(){
        return type;
    }

    public boolean isBomb(){
        return type == CellType.BOMB;
    }

    public int bombAround(){
        return bombAround;
    }

    public boolean isExplored(){
        return explored;
    }

    public boolean isNoBombAround(){
        return bombAround == 0;
    }
}