package sk.tuke.game.core;

public class main {

    public static void main(String[] args) {
        //System.out.println("Hello world");

        var field = new Field(3,3);

        for(int row =0; row < field.getRowCount(); row++) {
            for(int column = 0; column < field.getColumnCount(); column++) {
                var Pixel = field.getPixel(row, column);
                System.out.print("- ");
            }
            System.out.println();
        }
    }
}
