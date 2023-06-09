package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.Post;
import com.example.feedy.repositories.LikesRepository;
import com.example.feedy.repositories.PostRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class FeedsController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;


    @FXML
    private ScrollPane postScrollPane;
    PostRepository postRepository = new PostRepository();
    LikesRepository likesRepository = new LikesRepository();


    @FXML
    void logout(MouseEvent event) {
        AppState.stateLogout();
        redirectToLoginPage();
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //creating a Vbox to contain the posts
        VBox allPostsContainer = new VBox();
        allPostsContainer.getStyleClass().add("all_posts_container");

        //making the items of the posts and adding them to the scroll pane
        List<Post> posts = postRepository.getAllPosts();

        for (Post post : posts) {
            VBox postContainer = new VBox();
            postContainer.getStyleClass().add("post_container");
            VBox.setMargin(postContainer, new Insets(5, 10, 5, 10));

            //making the hbox that contains the owner and his profile picture
            HBox ownerContainer = new HBox();
            ownerContainer.getStyleClass().add("post_owner_container");
            //making the profile picture
            File file = new File(post.user.profile_picture);
            String localUrl = null;
            try {
                localUrl = file.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            ImageView imageView = new ImageView(new Image(localUrl));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            ownerContainer.getChildren().add(imageView);
            //making the vbox where the writer and the date go
            VBox writerContainer = new VBox();
            //adding a left margin to the vbox
            writerContainer.getStyleClass().add("post_writer_container");
            //making the username
            Label writerLabel = new Label(post.user.username);
            writerLabel.getStyleClass().add("post_owner");
            //making the date
            Label dateLabel = new Label(ProfileController.formatDate(post.created_at));
            dateLabel.getStyleClass().add("post_date");
            writerContainer.getChildren().addAll(writerLabel, dateLabel);
            ownerContainer.getChildren().add(writerContainer);

            //the name of the owner is a link to his profile
            writerLabel.setOnMouseClicked(event -> {
                openProfile(post.user.id);
            });

            Label contentLabel = new Label(post.content);
            contentLabel.setWrapText(true);
            contentLabel.getStyleClass().add("post_content");

            //adding the like button and the number of likes
            HBox likeContainer = new HBox();
            likeContainer.getStyleClass().add("post_like_container");
            Button likeButton = new Button("Like");
            likeButton.getStyleClass().add("post_like");
            boolean isLiked = likesRepository.checkLike(AppState.currentUser, post.id);
            if (isLiked) {
                likeButton.setStyle("-fx-background-color: #1a91da; -fx-text-fill: white;");
            }
            int likesCount = likesRepository.getCount(post.id);
            Label likeCountLabel = new Label(likesCount + " like" + (likesCount > 1 ? "s" : ""));
            likeCountLabel.getStyleClass().add("post_like_count");
            //setting the onclick event for the like button
            likeButton.setOnAction(event -> {
                //checking if the user has already liked the post
                boolean isLiked1 = likesRepository.checkLike(AppState.currentUser, post.id);
                if (!isLiked1) { //adding the like
                    int likeResult = likesRepository.addLike(AppState.currentUser, post.id);
                    likeButton.setStyle("-fx-background-color: #1a91da; -fx-text-fill: white;");
                    //updating the number of likes and avoiding requesting the database again
                    String count = plusOne(likeCountLabel.getText().split(" ")[0]);
                    likeCountLabel.setText(count + " like" + (Integer.parseInt(count) > 1 ? "s" : ""));
                } else { //canceling the like
                    likesRepository.cancelLike(AppState.currentUser, post.id);
                    likeButton.setStyle("-fx-background-color: #eeeeee; -fx-text-fill: black;");
                    //updating the number of likes and avoiding requesting the database again
                    String count = minusOne(likeCountLabel.getText().split(" ")[0]);
                    likeCountLabel.setText(count + " like" + (Integer.parseInt(count) > 1 ? "s" : ""));
                }
            });

            //adding all elements to the post container
            likeContainer.getChildren().addAll(likeButton, likeCountLabel);
            postContainer.getChildren().addAll(ownerContainer, contentLabel, likeContainer);
            allPostsContainer.getChildren().add(postContainer);
        }
        postScrollPane.setContent(allPostsContainer);
        postScrollPane.setFitToWidth(true);
    }

    public void redirectToLoginPage() {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("login_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) postScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openProfile(int id) {
        AppState.stateVisitedUser(id);
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("profile_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) postScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void openCreatePostView(MouseEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("create_post_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) postScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openPersonalProfile(MouseEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("personal_profile_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) postScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToGroupChat(MouseEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("group_chat_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) postScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String plusOne(String number) {
        try {
            int num = Integer.parseInt(number);
            num++;
            return String.valueOf(num);
        } catch (Exception e) {
            return number;
        }
    }

    public static String minusOne(String number) {
        try {
            int num = Integer.parseInt(number);
            num--;
            return num >= 0 ? String.valueOf(num) : "0";
        } catch (Exception e) {
            return number;
        }
    }
}
