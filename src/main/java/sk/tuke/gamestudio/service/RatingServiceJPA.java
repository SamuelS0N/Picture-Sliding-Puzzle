package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws GameStudioException {
        try {
            int ratingGet = getRating(rating.getGame(), rating.getPlayer());
            if (ratingGet == 0) {
                entityManager.persist(rating);
            } else {
                int newRating = rating.getRating();
                entityManager.createQuery("UPDATE Rating SET rating = :newRating where player = :player")
                        .setParameter("newRating", newRating)
                        .setParameter("player", rating.getPlayer()).executeUpdate();
            }
        } catch (GameStudioException exception) {
        }
    }

    @Override
    public double getAverageRating(String game) throws GameStudioException {
        try  {
            return (double) entityManager.createQuery("SELECT AVG(r.rating) from Rating r where r.game = :game")
                    .setParameter("game", game)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public int getRating(String game, String player) throws GameStudioException {
        try {
            return (int) entityManager.createNativeQuery("SELECT rating from Rating s where s.game = :game and s.player = :player")
                    .setParameter("game", game)
                    .setParameter("player", player).getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNativeQuery("DELETE FROM Rating").executeUpdate();
    }
}
