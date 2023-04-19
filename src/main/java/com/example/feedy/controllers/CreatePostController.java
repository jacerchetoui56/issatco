package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.repositories.PostRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePostController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea postContent;

    @FXML
    private Label errorMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //make the textarea scrollable to the bottom
        postContent.textProperty().addListener((observable, oldValue, newValue) -> {
            postContent.setScrollTop(Double.MAX_VALUE);
        });
    }

    @FXML
    void createPost(ActionEvent event) {
        String postContent = this.postContent.getText();
        System.out.println("creating the post with content: " + postContent);

        //adding the post to the DB
        PostRepository postRepository = new PostRepository();
        int result = postRepository.createPost(AppState.currentUser, postContent);
        if (result == 0) {
            errorMessage.setText("Error: could not create the post");
            return;
        }

        //after the success of the post
        goToHomePage(null);
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
            Stage currentStage = (Stage) postContent.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void logout(ActionEvent event) {
        AppState.stateLogout();
        goToHomePage(null);
    }


}
