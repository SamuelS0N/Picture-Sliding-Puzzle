package sk.tuke.gamestudio.game;

public class Nonempty extends Pixel{

    private int value;

   public Nonempty(int value) {
       this.value = value;
   }

    public int getValue() {
        return this.value;
    }
}
