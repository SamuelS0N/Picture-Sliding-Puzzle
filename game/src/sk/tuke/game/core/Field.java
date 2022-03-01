package sk.tuke.game.core;

public class Field {

    private final Pixel[][] pixels;

    private final int rowCount;
    private final int columnCount;
    private int score;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.score = 0;

        pixels = new Pixel[rowCount][columnCount];
        generate();
    }

    private void generate() {
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Pixel getPixel(int row, int column) {
        return pixels[row][column];
    }
}
