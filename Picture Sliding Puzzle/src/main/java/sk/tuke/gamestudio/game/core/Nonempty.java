package sk.tuke.gamestudio.game.core;

public class Nonempty extends Pixel{

    private int value;

   public Nonempty(int value) {
       this.value = value;
   }

    public int getValue() {
        return this.value;
    }

/*    public void setValue(int value) {
        this.value = value;
    }*/
}
