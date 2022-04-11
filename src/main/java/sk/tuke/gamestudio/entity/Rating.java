package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private int ident;

    private String player;

    private String game;

    private int rating;

    private Date playedAt;

    public Rating() {
    }

    public Rating(String player, String game, int rating, Date playedAt) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.playedAt = playedAt;
    }

    public String getPlayer() { return player; }

    public void setPlayer(String player) { this.player = player; }

    public String getGame() { return game; }

    public void setGame(String game) { this.game = game; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public Date getPlayedAt() { return playedAt; }

    public void setPlayedAt(Date playedAt) { this.playedAt = playedAt; }

    @Override
    public String toString() {
        return "Rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating +
                ", playedAt=" + playedAt +
                '}';
    }
}
