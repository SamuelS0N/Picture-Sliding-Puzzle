package sk.tuke.gamestudio.game.core;

public abstract class Pixel {

    private PixelState state;

    public PixelState getState() {
        return this.state;
    }

    void setState(PixelState state) {
        this.state = state;
    }

}
