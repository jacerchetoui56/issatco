package com.example.feedy.repositories;

import com.example.feedy.MyConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PostRepository {

    Connection connection = null;
    Statement statement = null;

    public PostRepository() {
        connection = MyConnexion.connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int createPost(int user_id, String postText){
        if (connection != null) {
            String requete = "insert into posts(user_id, body) values(?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, user_id);
                preparedStatement.setString(2, postText);
                System.out.println("post created with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while creating post : " + e.getMessage());
            }
        }
        return 0;
    }

    //deleting a post
    public int deletePost(String post_id){
        if (connection != null) {
            String requete = "delete from posts where id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, post_id);
                System.out.println("post deleted with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while deleting post : " + e.getMessage());
            }
        }
        return 0;
    }

    //updating a post
    public int updatePost(String post_id, String postText){
        if (connection != null) {
            String requete = "update posts set body=? where id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, postText);
                preparedStatement.setString(2, post_id);
                System.out.println("post updated with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while updating post : " + e.getMessage());
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        //creating a new instance of the post repository
        PostRepository postRepository = new PostRepository();

        //adding the first post
        //postRepository.createPost("114", "this is a test post added by jacer");

        //adding the second post to be deleted later
        //postRepository.createPost("114", "this is a test post added by jacer to be deleted later");

        //deleting the last post of the id 2
        //postRepository.deletePost("2");

        //updating the first post
         //postRepository.updatePost("1", "this is a test post added by jacer to be updated later");
    }

}
