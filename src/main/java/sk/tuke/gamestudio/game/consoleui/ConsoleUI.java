package sk.tuke.gamestudio.game.consoleui;

import sk.tuke.gamestudio.game.core.Empty;
import sk.tuke.gamestudio.game.core.Field;
import sk.tuke.gamestudio.game.core.FieldState;
import sk.tuke.gamestudio.game.core.Nonempty;
import sk.tuke.gamestudio.game.entity.Comment;
import sk.tuke.gamestudio.game.entity.Rating;
import sk.tuke.gamestudio.game.entity.Score;
import sk.tuke.gamestudio.game.service.*;

import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {

    private static final Pattern INPUT_PATTERN = Pattern.compile("([M])([1-4])([A-D])");
    private static final Pattern INPUT_PATTERN_RATING = Pattern.compile("([1-3])");

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);

    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        //when at start is solved
        if(field.getFieldState() == FieldState.SOLVED) {
            printField();
            System.out.println("You are lucky man :) Game Solved!");
            return;
        }

        printTopScores();
        printTopComments();
        printAverageRating();
        do {
            printField();
            processInput();
        } while (field.getFieldState() == FieldState.PLAYING);
        printField();

        System.out.println("Game Solved!");
        scoreService.addScore(new Score(System.getProperty("user.name"), "Picture Sliding Puzzle", field.getScore(), new Date()));
        System.out.print("Enter comment: ");
        String text = scanner.next();
        commentService.addComment(new Comment(System.getProperty("user.name"), "Picture Sliding Puzzle", text, new Date()));
        processInputRating();
    }

    private void printField() {
        System.out.print("  ");
        for(int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print((char) ('A' + column));
        }
        System.out.println();
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print(row+1+ ". ");
            for (int column = 0; column < field.getColumnCount(); column++) {

                if (field.getPixel(row, column) instanceof Nonempty) {
                    System.out.print(((Nonempty) field.getPixel(row, column)).getValue());
                }
                else if (field.getPixel(row, column) instanceof Empty) {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Score: " + field.getScore());
    }

    private void processInputRating() {
        System.out.print("Enter rating(1-3[best]): ");
        var line = scanner.next();
        var matcher = INPUT_PATTERN_RATING.matcher(line);
        if(matcher.matches()) {
            ratingService.setRating(new Rating(System.getProperty("user.name"), "Picture Sliding Puzzle", Integer.parseInt(matcher.group(1)), new Date()));
        } else {
            System.out.println("Wrong input. Maybe Later >{");
        }
    }

    private void processInput() {
        System.out.print("Enter command(X - exit, Mxy- move(int x, int y)");
        var line = scanner.nextLine().toUpperCase();
        System.out.println(line);

        if("X".equals(line))
            System.exit(0);

        var matcher = INPUT_PATTERN.matcher(line);
        if(matcher.matches()) {
            int row = Integer.parseInt(matcher.group(2)) - 1;
            int column = matcher.group(3).charAt(0) - 'A';

            if(row > field.getRowCount()-1 || column > field.getColumnCount() -1) {
                System.out.println("Bad index!");
                return;
            }

            if("M".equals(matcher.group(1)))
                field.moveTile(row,column);
        } else {
            System.out.println("Wrong input");
        }
    }


    private void printTopScores() {
        var scores = scoreService.getTopScore("Picture Sliding Puzzle");
        System.out.println("-----------------------------------------------");
        System.out.println("Top 10 scores:");
        for(int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d \n",i+1,  score.getPlayer(), score.getPoints());
        }
        System.out.println("-----------------------------------------------");
    }

    private void printTopComments() {
        var comments = commentService.getComments("Picture Sliding Puzzle");
        System.out.println("-----------------------------------------------");
        System.out.println("Top 10 comments:");
        for(int i = 0; i < comments.size(); i++) {
            var comment = comments.get(i);
            System.out.printf("%d. %s %s \n",i+1,  comment.getPlayer(), comment.getText());
        }
        System.out.println("-----------------------------------------------");
    }

    private void printAverageRating() {
        double average = ratingService.getAverageRating("Picture Sliding Puzzle");
        System.out.println("-----------------------------------------------");
        System.out.println("Average rating of this game is: " + average);
        System.out.println("-----------------------------------------------");
    }

}
