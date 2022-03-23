package sk.tuke.gamestudio.game.service;

import org.junit.Test;
import sk.tuke.gamestudio.game.entity.Comment;


import java.util.Date;

import static org.junit.Assert.*;

public class CommentServiceTest {

    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset(){
        commentService.reset();
        assertEquals(0, commentService.getComments("Picture Sliding Puzzle").size());
    }

    @Test
    public void testAddComment() {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("Jano", "Picture Sliding Puzzle", "okej", date));

        var comments = commentService.getComments("Picture Sliding Puzzle");
        assertEquals(1, comments.size());
        assertEquals("Jano", comments.get(0).getPlayer());
        assertEquals("Picture Sliding Puzzle", comments.get(0).getGame());
        assertEquals("okej", comments.get(0).getText());
        assertEquals(date, comments.get(0).getPlayedAt());
    }

    @Test
    public void testGetTopComment() {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("jano","Picture Sliding Puzzle", "ok", date));
        commentService.addComment(new Comment("katka","Mines", "good", date));
        commentService.addComment(new Comment("zuzka","Mines", "bad", date));
        commentService.addComment(new Comment("milan","Picture Sliding Puzzle", "nice", date));

        var comments = commentService.getComments("Picture Sliding Puzzle");
        assertEquals(2, comments.size());

        assertEquals("Picture Sliding Puzzle", comments.get(0).getGame());
        assertEquals("jano", comments.get(0).getPlayer());
        assertEquals("ok", comments.get(0).getText());

        assertEquals("Picture Sliding Puzzle", comments.get(1).getGame());
        assertEquals("milan", comments.get(1).getPlayer());
        assertEquals("nice", comments.get(1).getText());
    }

    @Test
    public void testGetTopCommentEmpty() {
        commentService.reset();

        var comments = commentService.getComments("Picture Sliding Puzzle");
        assertEquals(0, comments.size());
    }
}
