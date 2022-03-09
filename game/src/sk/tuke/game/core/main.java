package sk.tuke.game.core;

import sk.tuke.game.consoleui.ConsoleUI;

public class main {

    public static void main(String[] args) {

        var field = new Field(3,3);
        var ui = new ConsoleUI(field);
        ui.play();

        field.moveTile(0,1);
        ui.play();
        field.controlPrintPixelStates();
    }


}
