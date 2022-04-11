package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.GameStudioException;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating) throws GameStudioException {
        ratingService.setRating(rating);
    }

    @GetMapping("/{game}")
    public double getAverageRating(@PathVariable String game) throws GameStudioException {
        return ratingService.getAverageRating(game);
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game,@PathVariable String player) throws GameStudioException {
        return ratingService.getRating(game, player);
    }
}
