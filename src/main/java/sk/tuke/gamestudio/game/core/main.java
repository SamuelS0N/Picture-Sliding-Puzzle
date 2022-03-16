package sk.tuke.gamestudio.game.core;

import sk.tuke.gamestudio.game.consoleui.ConsoleUI;

public class main {

    public static void main(String[] args) {

        var field = new Field(2,2);
        var ui = new ConsoleUI(field);

        ui.play();
    }


}
