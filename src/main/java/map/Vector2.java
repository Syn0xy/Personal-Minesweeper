package map;

public class Vector2 {
    private static final int X = 0;
    private static final int Y = 0;

    public int x;
    public int y;

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        this(X, Y);
    }

    public Vector2 copy(){
        return new Vector2(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        if (x != other.x) return false;
        if (y != other.y) return false;
        return true;
    }
}
