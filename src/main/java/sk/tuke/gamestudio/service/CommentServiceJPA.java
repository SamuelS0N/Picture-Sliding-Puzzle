package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) throws GameStudioException {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) throws GameStudioException {
        return (List<Comment>) entityManager.createQuery("select s from Comment s where s.game = :game")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() throws GameStudioException {
        entityManager.createNativeQuery("DELETE FROM Comment").executeUpdate();
    }
}
