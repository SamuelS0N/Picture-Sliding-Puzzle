package sk.tuke.gamestudio.game.core;


import java.util.*;


public class Field {

    private final Pixel[][] pixels;

    private final int rowCount;
    private final int columnCount;
    private int score;

    private FieldState fieldState;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.score = 0;

        this.fieldState = FieldState.PLAYING;

        pixels = new Pixel[rowCount][columnCount];
        generate();
    }

    private void generate() {
        generateEmptyPixel();
        fillWithNonemptyPixels();
        updatePixelsStates();
        //controlPrintPixelStates();
    }

    private void generateEmptyPixel() {
        Random random = new Random();
        int row = random.nextInt(rowCount);
        int column = random.nextInt(columnCount);
        pixels[row][column] = new Empty();
        pixels[row][column].setState(PixelState.UNMOVABLE);

    }

    private void fillWithNonemptyPixels() {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= columnCount*rowCount-1; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        int a = 0;
        for (int row =0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if(pixels[row][column] == null) {

                    pixels[row][column] = new Nonempty(list.get(a));
                    a++;

                }
            }
        }
    }

    public void controlPrintPixelStates() {
        for (int row =0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if(pixels[row][column].getState()== PixelState.UNMOVABLE) {
                    System.out.print("UNMOVABLE");
                    System.out.print(" ");
                }
                if(pixels[row][column].getState()== PixelState.MOVABLEUPDOWN) {
                    System.out.print("MOVABLEUPDOWN");
                    System.out.print(" ");
                }
                if(pixels[row][column].getState()== PixelState.MOVABLERIGHTLEFT) {
                    System.out.print("MOVABLERIGHTLEFT");
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void updatePixelsStates() {

        for (int row =0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                    pixels[row][column].setState(PixelState.UNMOVABLE);
            }
        }

        int[] positionEmpty = getPositionEmpty();
        int rowEmpty = positionEmpty[0];
        int columnEmpty = positionEmpty[1];

        for(int column = 0; column< getColumnCount(); column++) {
                pixels[rowEmpty][column].setState(PixelState.MOVABLERIGHTLEFT);
        }

        for(int row = 0; row < getRowCount(); row++) {
                pixels[row][columnEmpty].setState(PixelState.MOVABLEUPDOWN);
        }
        pixels[rowEmpty][columnEmpty].setState(PixelState.UNMOVABLE);
    }


    public void moveTile(int row, int column) {

        if(getFieldState() == FieldState.SOLVED) return;

        Pixel pixel = getPixel(row, column);
        if(pixel.getState() == PixelState.UNMOVABLE || pixel instanceof Empty) return;

        int[] positionEmpty = getPositionEmpty();
        int positionEmptyRow = positionEmpty[0];
        int positionEmptyColumn = positionEmpty[1];


        if(pixel.getState() == PixelState.MOVABLEUPDOWN) {

            movingTilesUpDown(row, column, positionEmptyRow);

        }
        else if (pixel.getState() == PixelState.MOVABLERIGHTLEFT) {

            movingTilesRightLeft(row, column, positionEmptyColumn);

        }
        updatePixelsStates();
        increaseScore();
        endIfSolved();


    }

    private void endIfSolved() {

        int controlValue = 1;
        for(int row = 0; row < getRowCount(); row++) {

            for (int column = 0; column < getColumnCount(); column++) {

                if(controlValue != rowCount*columnCount) {
                        if(getPixel(row, column) instanceof Empty) return;
                        Nonempty nonempty = (Nonempty) pixels[row][column];
                        if (nonempty.getValue() != controlValue) {
                            return;
                        }
                     controlValue++;
                }
            }
        }
        fieldState = FieldState.SOLVED;
    }

    private void movingTilesRightLeft(int row, int column, int positionEmptyColumn) {
        if (column - positionEmptyColumn > 0) {
            for (int a = positionEmptyColumn; a < (Math.abs(column - positionEmptyColumn)) + positionEmptyColumn; a++) {
                Pixel currentPixel = pixels[row][a];

                pixels[row][a] = pixels[row][a+1];
                pixels[row][ a+1 ] = currentPixel;
            }
        } else {
            for (int a = positionEmptyColumn; a > column; a--) {
                Pixel currentPixel = pixels[row][a];

                pixels[row][a] = pixels[row][a-1];
                pixels[row][a-1] = currentPixel;
            }
        }
    }

    private void movingTilesUpDown(int row, int column, int positionEmptyRow) {
        if (row - positionEmptyRow > 0) {
            for (int a = positionEmptyRow; a < (Math.abs(row - positionEmptyRow)) + positionEmptyRow; a++) {
                Pixel currentPixel = pixels[a][column];

                pixels[a][column] = pixels[a + 1][column];
                pixels[a + 1][column] = currentPixel;
            }
        } else {
            for (int a = positionEmptyRow; a > row; a--) {
                Pixel currentPixel = pixels[a][column];

                pixels[a][column] = pixels[a - 1][column];
                pixels[a - 1][column] = currentPixel;
            }
        }
    }

    private int[] getPositionEmpty() {
        int row = 0;
        int column = 0;
        for (int rowEmpty = 0; rowEmpty < getRowCount(); rowEmpty++) {
            for( int columnEmpty =0; columnEmpty < getColumnCount(); columnEmpty++) {
                if(pixels[rowEmpty][columnEmpty] instanceof Empty) {
                    row = rowEmpty;
                    column = columnEmpty;
                }
            }
        }
        return new int[] {row, column};
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

    public void increaseScore() {
        this.score++;
    }

    public Pixel getPixel(int row, int column) {
        return pixels[row][column];
    }

    public FieldState getFieldState() {
        return this.fieldState;
    }
}
