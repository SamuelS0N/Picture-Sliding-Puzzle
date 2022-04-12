package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService{

    private String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public double getAverageRating(String game) throws GameStudioException {
        return restTemplate.getForObject(url + "/" + game, double.class);
    }

    @Override
    public int getRating(String game, String player) throws GameStudioException {
        return restTemplate.getForObject(url + "/" + game + "/" + player, int.class);
    }

    @Override
    public void reset() throws GameStudioException {
        throw new UnsupportedOperationException("Reset is not supported on eb interface");
    }
}
