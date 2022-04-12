package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;

import static org.junit.Assert.*;

public class RatingServiceTest {

    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testReset() {
        ratingService.reset();
        assertEquals(-1, ratingService.getRating("Picture Sliding Puzzle", "Jano"));
    }

    @Test
    public void testAddRating() {
        ratingService.reset();

        var date = new Date();
        ratingService.setRating(new Rating("Jano", "Picture Sliding Puzzle", 3, date));

        assertEquals(3,ratingService.getRating("Picture Sliding Puzzle", "Jano" ));
    }

    @Test
    public void testAverageRating() {
        ratingService.reset();

        var date = new Date();
        ratingService.setRating(new Rating("Jano", "Picture Sliding Puzzle", 3, date));
        ratingService.setRating(new Rating("Zuzka", "Picture Sliding Puzzle", 3, date));
        ratingService.setRating(new Rating("aNNA", "Picture Sliding Puzzle", 1, date));
        ratingService.setRating(new Rating("Milan", "Mines", 2, date));

        double average = ratingService.getAverageRating("Picture Sliding Puzzle");
        assertEquals((double) 7/3, average, 0);
    }

    @Test
    public void  testGetRating() {
        ratingService.reset();
        var date = new Date();
        ratingService.setRating(new Rating("Jano", "Picture Sliding Puzzle", 3, date));
        int rating = ratingService.getRating("Picture Sliding Puzzle", "Jano");
        assertEquals(3, 3);

        ratingService.reset();
        ratingService.setRating(new Rating("Jano", "Mines", 3, date));
        rating = ratingService.getRating("Picture Sliding Puzzle", "Jano");
        assertEquals(-1, rating);
    }
}
