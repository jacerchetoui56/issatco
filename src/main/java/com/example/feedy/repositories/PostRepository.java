package com.example.feedy.repositories;

import com.example.feedy.MyConnexion;
import com.example.feedy.Post;
import com.example.feedy.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    public List<Post> getAllPosts(){
        List<Post> posts = new ArrayList<>();
        if (connection != null) {
            String requete = "SELECT posts.id, posts.body, posts.created_at, users.id AS user_id, users.profile_picture, users.username FROM posts INNER JOIN users ON posts.user_id = users.id ORDER BY created_at DESC LIMIT 20";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()){
                    int id = result.getInt("id");
                    int user_id = result.getInt("user_id");
                    String body = result.getString("body");
                    String username = result.getString("username");
                    String profile_picture = result.getString("profile_picture");
                    String created_at = result.getString("created_at");
                    User user = new User(user_id, username, profile_picture);
                    Post post = new Post(id, body, user, created_at);
                    posts.add(post);
                }
            } catch (SQLException e) {
                System.out.println("error while getting all posts : " + e.getMessage());
            }
        }
        //returning an empty list if there is no connection
        return posts;
    }

    public Post getSinglePost(int id) {
        if (connection != null) {
            String requete = "SELECT posts.id, posts.body, posts.created_at, users.id AS user_id, users.profile_picture, users.username FROM posts INNER JOIN users ON posts.user_id = users.id WHERE posts.id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, id);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    int postId = result.getInt("id");
                    String body = result.getString("body");
                    String createdAt = result.getString("created_at");
                    int userId = result.getInt("user_id");
                    String username = result.getString("username");
                    String profilePicture = result.getString("profile_picture");
                    User user = new User(userId, username, profilePicture);
                    return new Post(postId, body, user, createdAt);
                }
            } catch (SQLException e) {
                System.out.println("Error while getting single post: " + e.getMessage());
            }
        }
        return null;
    }

    public List<Post> getUserPosts(int id){
        List<Post> posts = new ArrayList<>();
        if (connection != null) {
            String requete = "SELECT posts.id, posts.body, posts.created_at, users.id AS user_id, users.profile_picture, users.username FROM posts INNER JOIN users ON posts.user_id = users.id WHERE posts.user_id = ? ORDER BY created_at DESC LIMIT 20";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, id);
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()){
                    int postId = result.getInt("id");
                    String body = result.getString("body");
                    String createdAt = result.getString("created_at");
                    int userId = result.getInt("user_id");
                    String username = result.getString("username");
                    String profilePicture = result.getString("profile_picture");
                    User user = new User(userId, username, profilePicture);
                    Post post = new Post(postId, body, user, createdAt);
                    posts.add(post);
                }
            } catch (SQLException e) {
                System.out.println("error while getting all posts : " + e.getMessage());
            }
        }
        //returning an empty list if there is no connection
        return posts;
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

        //getting the post with id 115
//        Post post = postRepository.getSinglePost(3);
//        System.out.println(post);

        //getting the list of all posts
        List<Post> posts = postRepository.getAllPosts();
        for (Post post : posts) {
            System.out.println(post);
        }
    }

}
