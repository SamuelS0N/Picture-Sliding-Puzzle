package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.game.entity.Comment;
import sk.tuke.gamestudio.game.entity.Rating;
import sk.tuke.gamestudio.game.entity.Score;
import sk.tuke.gamestudio.game.service.*;

import java.util.Date;

public class TestJDBC {
    public static void main(String[]args) throws Exception {
        //SCORE
        ScoreService service = new ScoreServiceJDBC();

        service.reset();
        service.addScore(new Score("jaro", "Picture Sliding Puzzle", 20, new Date()));
        service.addScore(new Score("miro", "Picture Sliding Puzzle", 30, new Date()));
        service.addScore(new Score("duro", "Picture Sliding Puzzle", 15, new Date()));
        service.addScore(new Score("jano", "Picture Sliding Puzzle", 10, new Date()));

        var scores = service.getTopScore("Picture Sliding Puzzle");
        System.out.println(scores);

        //COMMENT
        CommentService comment = new CommentServiceJDBC();

        comment.reset();
        comment.addComment(new Comment("jaro", "Picture Sliding Puzzle", "da sa", new Date()));
        comment.addComment(new Comment("miro", "Picture Sliding Puzzle", "okej", new Date()));
        comment.addComment(new Comment("jano", "Picture Sliding Puzzle", "sahse", new Date()));
        comment.addComment(new Comment("milan", "Picture Sliding Puzzle", "oprav", new Date()));

        var comments = comment.getComments("Picture Sliding Puzzle");
        System.out.println(comments);

        //RATING
        RatingService rating = new RatingServiceJDBC();

        rating.reset();
        rating.setRating(new Rating("jaro", "Picture Sliding Puzzle", 1, new Date()));
        rating.setRating(new Rating("miro", "Picture Sliding Puzzle", 2, new Date()));
        rating.setRating(new Rating("jano", "Picture Sliding Puzzle", 3, new Date()));
        rating.setRating(new Rating("milan", "Picture Sliding Puzzle", 1, new Date()));

        double averageRating = rating.getAverageRating("Picture Sliding Puzzle");
        System.out.println(averageRating);

        int ratingNumber = rating.getRating("Picture Sliding Puzzle", "jaro");
        System.out.println(ratingNumber);
    }
}
