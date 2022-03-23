package sk.tuke.gamestudio.game.service;

import org.junit.Test;
import sk.tuke.gamestudio.game.entity.Score;

import java.util.Date;

import static org.junit.Assert.*;

public class ScoreServiceTest {

    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset() {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScore("jano").size());
    }

    @Test
    public void testAddScore() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("jano","Picture Sliding Puzzle", 10, date));

        var scores = scoreService.getTopScore("Picture Sliding Puzzle");
        assertEquals(1, scores.size());
        assertEquals("jano", scores.get(0).getPlayer());
        assertEquals("Picture Sliding Puzzle", scores.get(0).getGame());
        assertEquals(10, scores.get(0).getPoints());
        assertEquals(date,scores.get(0).getPlayedAt() );
    }

    @Test
    public void testGetTopScore() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("jano","Picture Sliding Puzzle", 10, date));
        scoreService.addScore(new Score("katka","mines", 23, date));
        scoreService.addScore(new Score("zuzka","mines", 4, date));
        scoreService.addScore(new Score("milanko","Picture Sliding Puzzle", 12, date));

        var scores = scoreService.getTopScore("Picture Sliding Puzzle");
        assertEquals(2, scores.size());

        assertEquals("Picture Sliding Puzzle", scores.get(0).getGame());
        assertEquals("jano", scores.get(0).getPlayer());
        assertEquals(10, scores.get(0).getPoints());

        assertEquals("Picture Sliding Puzzle", scores.get(1).getGame());
        assertEquals("milanko", scores.get(1).getPlayer());
        assertEquals(12, scores.get(1).getPoints());
    }

    @Test
    public void testGetTopScoreEmpty() {
        scoreService.reset();

        var scores = scoreService.getTopScore("Picture Sliding Puzzle");
        assertEquals(0, scores.size());
    }
}
