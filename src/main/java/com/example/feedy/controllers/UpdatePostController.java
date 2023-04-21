package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.Post;
import com.example.feedy.repositories.PostRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdatePostController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Label errorMessage;

    @FXML
    private Button logoutButton;

    @FXML
    private TextArea postContent;

    private PostRepository postRepository = new PostRepository();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //filling the textarea with the body of the post to be updated
        Post post = postRepository.getSinglePost(AppState.postToUpdate);
        postContent.setText(post.content);


    }

    @FXML
    void updatePost(ActionEvent event) {
        postRepository.updatePost(AppState.postToUpdate, postContent.getText());
        backToPersonalProfile();
    }

    @FXML
    void goToHomePage(MouseEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("feeds_view.fxml"));
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

    public void backToPersonalProfile(){
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("personal_profile_view.fxml"));
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
    void logout(ActionEvent event) {

    }

}
