package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.Empty;
import sk.tuke.gamestudio.game.Field;
import sk.tuke.gamestudio.game.FieldState;
import sk.tuke.gamestudio.game.Nonempty;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/Picture Sliding Puzzle")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {
    @Autowired
    private UserController userController;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;


    private Field field = new Field(2,2);


    @RequestMapping
    public String puzzle(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column,  Model model) {
        if(field.getFieldState() != FieldState.SOLVED) {
            if (row != null && column != null) {
                field.moveTile(row, column);
                if (field.getFieldState() == FieldState.SOLVED && userController.isLogged()) {
                    scoreService.addScore(new Score(userController.getLoggedUser().getLogin(), "Picture Sliding Puzzle", field.getScore(), new Date()));
                }
            }
        }
        fillModel(model);
        return "puzzle";
    }

    @RequestMapping("/new")
    public String newGame(@RequestParam(required = false) Integer level, Model model) {
        field = new Field(level,level);
        fillModel(model);
        return "puzzle";
    }

    @RequestMapping("/addRating")
    public String addRating(@RequestParam(required = false) Integer newRating) {

        if (newRating != null)
            ratingService.setRating(new Rating(userController.getLoggedUser().getLogin(), "Picture Sliding Puzzle", newRating, new Date()));
        return "redirect:/Picture Sliding Puzzle";
    }

    public String getState() {
        return field.getFieldState().toString();
    }

    public Integer getScore() { return  field.getScore(); }

    private void fillModel(Model model) {
        model.addAttribute("message", "This is message");
        model.addAttribute("scores", scoreService.getTopScore("Picture Sliding Puzzle"));
        model.addAttribute("comments", commentService.getComments("Picture Sliding Puzzle"));
        model.addAttribute("rating", ratingService.getAverageRating("Picture Sliding Puzzle"));
        //model.addAttribute("htmlField", getHtmlField());
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table id = 'tb1'>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                sb.append("<td>");
                if (field.getPixel(row, column) instanceof Nonempty) {
                    sb.append("<a href='/Picture Sliding Puzzle?row=" + row + "&column=" + column + "'>\n");
                    sb.append("<img src='/images/" + ((Nonempty) field.getPixel(row, column)).getValue() + ".png'>");
                }
                else if (field.getPixel(row, column) instanceof Empty) {
                    sb.append("<a href='/Picture Sliding Puzzle?row=" + row + "&column=" + column + "'>\n");
                    sb.append("<img src='/images/empty.png'>");
                }
                sb.append("</a>\n");
                sb.append("<td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("<table>\n");
        return sb.toString();
    }

    public String getCurrentTime() {
        return new Date().toString();
    }
}
