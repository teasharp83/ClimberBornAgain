/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package climberbornagain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Teagan
 */
public class HighScoreQueries {
    private static Connection connection;
    private static PreparedStatement getHighScores, checkNames, updateScore, addScore;
    private static ResultSet highScores, names;
    
    public static ArrayList<Player> getHighScores() {
        connection = DBConnection.getConnection();
        ArrayList<Player> scores = new ArrayList<>();
        try {
            getHighScores = connection.prepareStatement("select player, name, difficulty, score from app.highscores order by score");
            highScores = getHighScores.executeQuery();
            while (highScores.next()){
                Player score = new Player(highScores.getString(1), highScores.getString(2), highScores.getInt(3), highScores.getInt(4));
                scores.add(score);
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scores;
    }
    
    public static void updateScores(Player player) {
        connection = DBConnection.getConnection();
        ArrayList<String> playerNames = new ArrayList<>();
        try {
            checkNames = connection.prepareStatement("select difficulty, score from app.highscores where name = ?");
            checkNames.setString(1, player.getName());
            names = checkNames.executeQuery();
            if (names.next()) {
                if ((player.getScore() > names.getInt(2)) || ((player.getScore() == names.getInt(2)) && (player.getDifficulty() > names.getInt(1)))) {
                    updateScore = connection.prepareStatement("update app.highscores set player = ?, difficulty = ?, score = ? where name = ?");
                    updateScore.setString(1, player.getCharacter());
                    updateScore.setInt(2, player.getDifficulty());
                    updateScore.setInt(3, player.getScore());
                    updateScore.setString(4, player.getName());
                    updateScore.executeUpdate();
                }
            }
            else {
                addScore = connection.prepareStatement("insert into app.highscores (player, name, difficulty, score) values (?,?,?,?)");
                addScore.setString(1, player.getCharacter());
                addScore.setString(2, player.getName());
                addScore.setInt(3, player.getDifficulty());
                addScore.setInt(4, player.getScore());
                addScore.executeUpdate();
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}
