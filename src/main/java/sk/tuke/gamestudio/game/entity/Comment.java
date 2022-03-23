package sk.tuke.gamestudio.game.entity;

import java.util.Date;

public class Comment {

    private String player;

    private String game;

    private String text;

    private Date playedAt;

    public Comment(String player, String game, String text, Date playedAt) {
        this.player = player;
        this.game = game;
        this.text = text;
        this.playedAt = playedAt;
    }

    public String getPlayer() { return player;}

    public void setPlayer(String player) { this.player = player; }

    public String getGame() { return game; }

    public void setGame(String game) { this.game = game; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Date getPlayedAt() { return playedAt; }

    public void setPlayedAt(Date playedAt) { this.playedAt = playedAt; }

    @Override
    public String toString() {
        return "Comment{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", text='" + text + '\'' +
                ", playedAt=" + playedAt +
                '}';
    }
}
