import java.util.NoSuchElementException;
import java.util.Scanner;

import map.Minesweeper;
import view.MinesweeperView;

public class Platform{
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Minesweeper minesweeper = new Minesweeper();
                new MinesweeperView(minesweeper);
                play(minesweeper);
            }
        }).run();
        SCANNER.close();
    }

    private static final void play(Minesweeper minesweeper){
        while(true){
            System.out.println("Current round : " + minesweeper.getCurrentRound());
            int x = readIntNotNull("x : ");
            int y = readIntNotNull("y : ");
            minesweeper.explore(x, y);
            System.err.println();
        }
    }

    private static final int readIntNotNull(String s){
        System.out.print(s);
        return readIntNotNull();
    }

    private static final int readIntNotNull(){
        String s = null;
        int i = 0;
        while(s == null){
            try {
                s = SCANNER.nextLine();
                i = Integer.parseInt(s);
            } catch (NoSuchElementException | NumberFormatException e) {}
        }
        return i;
    }
}