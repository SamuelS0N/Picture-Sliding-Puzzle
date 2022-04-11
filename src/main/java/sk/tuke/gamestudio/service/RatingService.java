package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

public interface RatingService {

    void setRating(Rating rating) throws GameStudioException;
    double getAverageRating(String game) throws GameStudioException;
    int getRating(String game, String player) throws GameStudioException;
    void reset() throws GameStudioException;

}
