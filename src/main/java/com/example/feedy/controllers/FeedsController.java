package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.Post;
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
    private Button logoutButton;
    @FXML
    private ScrollPane postScrollPane;

    @FXML
    void logout(ActionEvent event) {
        AppState.stateLogout();
        redirectToLoginPage();
    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //creating a Vbox to contain the posts
        VBox allPostsContainer = new VBox();


        //making the items of the posts and adding them to the scroll pane
        PostRepository postRepository = new PostRepository();
        List<Post> posts = postRepository.getAllPosts();

        for (Post post : posts){
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
            writerContainer.getStyleClass().add("post_writer_container");
            //making the username
            Label writerLabel = new Label(post.user.username);
            writerLabel.getStyleClass().add("post_owner");
            writerContainer.getChildren().add(writerLabel);
            //making the date
            Label dateLabel = new Label(ProfileController.formatDate(post.created_at));
            dateLabel.getStyleClass().add("post_date");
            writerContainer.getChildren().add(dateLabel);
            ownerContainer.getChildren().add(writerContainer);

            //the name of the owner is a link to his profile
            writerLabel.setOnMouseClicked(event -> {
                openProfile(post.user.id);
            });

            Label contentLabel = new Label(post.content);
            contentLabel.setWrapText(true);
            contentLabel.getStyleClass().add("post_content");

            postContainer.getChildren().addAll( ownerContainer, contentLabel);
            allPostsContainer.getChildren().add(postContainer);
        }
        postScrollPane.setContent(allPostsContainer);
        postScrollPane.setFitToWidth(true);
        //sout the date to make sure to display the right format
        System.out.println(posts.get(0).created_at);
    }

    public void redirectToLoginPage(){
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("login_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) logoutButton.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openProfile(int id){
        AppState.stateVisitedUser(id);
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("profile_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) logoutButton.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void openCreatePostView(ActionEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("create_post_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) logoutButton.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
