package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class RatingServiceJDBC implements RatingService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "samo";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String INSERT_STATEMENT = "INSERT INTO rating(player, game, stars, played_at) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, stars, played_at FROM rating WHERE game = ? ORDER BY stars ASC LIMIT 10";

    @Override
    public void setRating(Rating rating) throws GameStudioException {

        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(INSERT_STATEMENT);
        ) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getPlayedAt().getTime()));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public double getAverageRating(String game) throws GameStudioException {

        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT);
        ) {
            statement.setString(1,game);
            try (var rs = statement.executeQuery()) {
                ArrayList<Integer> stars = new ArrayList<>();
                while (rs.next()) {
                    stars.add(rs.getInt(3));
                }

                int average = 0;
                for(int a = 0; a < stars.size(); a++) {
                    average = average + stars.get(a);
                }
                average = average / stars.size();

                return average;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String game, String player) throws GameStudioException {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT);
        ) {
            statement.setString(1,game);
            try (var rs = statement.executeQuery()) {

                while (rs.next()) {
                    if(rs.getString(1).equals(player)) {
                        return rs.getInt(3);
                    }
                }
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
        return -1;
    }

    @Override
    public void reset() throws GameStudioException {
        try(var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
