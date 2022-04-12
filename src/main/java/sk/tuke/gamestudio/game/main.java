package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.consoleui.ConsoleUI;

import java.util.InputMismatchException;
import java.util.Scanner;


public class main {

    public static void main(String[] args) {

/*        Scanner scan = new Scanner(System.in);
        boolean continueInput = true;
        int row = 0;

        do {
            try {
                System.out.print("Enter level(1-3): ");
                row = scan.nextInt();
                while (row > 3 || row < 1 ) {
                    System.out.print("Wrong input! Enter level(1-3): ");
                    row = scan.nextInt();
                }
                continueInput = false;

            } catch (InputMismatchException ex) {
                System.out.println("Try again. (" +
                        "Incorrect input: an integer is required)");
                scan.nextLine();
            }
        } while (continueInput);*/


        var field = new Field();
        var ui = new ConsoleUI(field);

        ui.play();
    }

}
