package com.example.feedy.repositories;

import com.example.feedy.MyConnexion;

import java.sql.*;

public class LikesRepository {
    Connection connection = null;
    Statement statement = null;

    public LikesRepository() {
        connection = MyConnexion.connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int addLike(int user_id, int post_id){
        if (connection != null) {
            String requete = "insert into likes(user_id, post_id) values(?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, user_id);
                preparedStatement.setInt(2, post_id);
                System.out.println("like added with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while adding like : " + e.getMessage());
            }
        }
        return 0;
    }

    public boolean checkLike(int user_id, int post_id){
        if (connection != null) {
            String requete = "select * from likes where user_id=? and post_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, user_id);
                preparedStatement.setInt(2, post_id);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    System.out.println("like found");
                    return true;
                } else {
                    System.out.println("like not found");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("error while checking like : " + e.getMessage());
            }
        }
        return false;
    }

    public int getCount(int post_id){
        if (connection != null) {
            String requete = "select count(*) from likes where post_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, post_id);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    System.out.println("count found");
                    return result.getInt(1);
                } else {
                    System.out.println("count not found");
                    return 0;
                }
            } catch (SQLException e) {
                System.out.println("error while getting count : " + e.getMessage());
            }
        }
        return 0;
    }

    public int cancelLike(int user_id, int post_id){
        if (connection != null) {
            String requete = "delete from likes where user_id=? and post_id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, user_id);
                preparedStatement.setInt(2, post_id);
                System.out.println("like canceled with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while canceling like : " + e.getMessage());
            }
        }
        return 0;
    }
}
