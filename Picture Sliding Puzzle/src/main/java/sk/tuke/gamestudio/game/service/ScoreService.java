package sk.tuke.gamestudio.game.service;

import sk.tuke.gamestudio.game.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getTopScore(String name);

    void reset();
}
