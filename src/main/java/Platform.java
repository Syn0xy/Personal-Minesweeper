import map.Minesweeper;
import view.GameView;

public class Platform{
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override public void run() {
                new GameView(new Minesweeper());
            }
        }).run();
    }
}