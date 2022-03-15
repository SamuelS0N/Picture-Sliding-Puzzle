package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.game.entity.Score;
import sk.tuke.gamestudio.game.service.ScoreService;
import sk.tuke.gamestudio.game.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {
    public static void main(String[]args) throws Exception {
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("jaro", "Picture Sliding Puzzle", 20, new Date()));
        service.addScore(new Score("miro", "Picture Sliding Puzzle", 30, new Date()));
        service.addScore(new Score("duro", "Picture Sliding Puzzle", 15, new Date()));
        service.addScore(new Score("jano", "Picture Sliding Puzzle", 10, new Date()));

        var scores = service.getTopScore("Picture Sliding Puzzle");
        System.out.println(scores);
    }
}
