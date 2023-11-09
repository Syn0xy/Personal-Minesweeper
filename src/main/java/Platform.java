import map.Minesweeper;
import view.MinesweeperView;

public class Platform{
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MinesweeperView(new Minesweeper());
            }
        }).run();
    }
}