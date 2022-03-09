package sk.tuke.game.consoleui;

import sk.tuke.game.core.Empty;
import sk.tuke.game.core.Field;
import sk.tuke.game.core.Nonempty;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    //nemam este vseobecne
    private static final Pattern INPUT_PATTERN = Pattern.compile("([M])([1-3])([A-C])");

    private Field field;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        while (true) {
            printField();
            processInput();
        }
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
}
