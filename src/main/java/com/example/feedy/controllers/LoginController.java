package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.repositories.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController  implements Initializable {
    //the items of the FXML login page
    @FXML
    private Label signupLink;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorMessage;
    @FXML
    private Label loading;

    private Stage stage;
    private Scene scene;
    private Parent root;
    UsersRepository usersRepository = new UsersRepository();




    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        //handling the enter key press event
        passwordField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    login(null);
                    break;
            }
        });
        emailField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    login(null);
                    break;
            }
        });
    }
    @FXML
    public void openRegisterPage(MouseEvent event) throws IOException {
        System.out.println("opening register page");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("register_view.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void redirectToHome() {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("feeds_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) emailField.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void login(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (usersRepository.login(email, password)) {
            System.out.println("login success");
            //redirecting the user to the feeds (home) page
            loading.setText("Loading...");
            redirectToHome();
        }
        else {
            System.out.println("login failed");
            errorMessage.setText("Invalid Credentials");
            emailField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px ;");
            passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ; -fx-border-radius: 5px ;");
//            focus on the email field
            emailField.requestFocus();

        }
    }

}
