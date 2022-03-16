package sk.tuke.gamestudio.game.consoleui;

import sk.tuke.gamestudio.game.core.Empty;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.FieldState;
import sk.tuke.gamestudio.game.core.Nonempty;
import sk.tuke.gamestudio.game.entity.Score;
import sk.tuke.gamestudio.game.service.ScoreService;
import sk.tuke.gamestudio.game.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    //nemam este vseobecne
    private static final Pattern INPUT_PATTERN = Pattern.compile("([M])([1-3])([A-C])");

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);

    private ScoreService scoreService = new ScoreServiceJDBC();

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        do {
            printTopScores();
            printField();
            processInput();
        } while (field.getFieldState() == FieldState.PLAYING);
        printField();
        System.out.println("Game Solved!");
        scoreService.addScore(new Score(System.getProperty("user.name"), "Picture Sliding Puzzle", field.getScore(), new Date()));
    }

    private void printField() {
        System.out.print("  ");
        for(int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print((char) ('A' + column));
        }
        System.out.println();
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print(row+1+ ". ");
            for (int column = 0; column < field.getColumnCount(); column++) {

                if (field.getPixel(row, column) instanceof Nonempty) {
                    System.out.print(((Nonempty) field.getPixel(row, column)).getValue());
                }
                else if (field.getPixel(row, column) instanceof Empty) {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Score: " + field.getScore());
    }

    private void processInput() {
        System.out.print("Enter command(X - exit, Mxy- move(int x, int y)");
        var line = scanner.nextLine().toUpperCase();
        System.out.println(line);

        if("X".equals(line))
            System.exit(0);

        var matcher = INPUT_PATTERN.matcher(line);
        if(matcher.matches()) {
            int row = Integer.parseInt(matcher.group(2)) - 1;
            int column = matcher.group(3).charAt(0) - 'A';
            if("M".equals(matcher.group(1)))
                field.moveTile(row,column);
        } else {
            System.out.println("Wrong input");
        }
    }

    private void printTopScores() {
        var scores = scoreService.getTopScore("Picture Sliding Puzzle");
        System.out.println("-----------------------------------------------");
        for(int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d \n",i+1,  score.getPlayer(), score.getPoints());
        }
        System.out.println("-----------------------------------------------");
    }

}
