package com.example.feedy.controllers;

import com.example.feedy.EmailValidator;
import com.example.feedy.Main;
import com.example.feedy.repositories.UsersRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterController implements Initializable {

    //fxml elements
    @FXML
    private Label loginLink;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField bioField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField pictureField;

    @FXML
    private Button signupButton;
    @FXML
    private Label fileChooserContainer;
    @FXML
    private Label filechooserindicator;
    @FXML
    private Label usedEmailLabel;
    UsersRepository usersRepository = new UsersRepository();
    private String picturePath = "";


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //making the file chooser
        filechooserindicator.setVisible(false);
        usedEmailLabel.setVisible(false);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        fileChooserContainer.setOnMouseClicked(event -> {
            picturePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
            filechooserindicator.setVisible(true);
        });
        //informing the user that he chose a file
    }

    @FXML
    void signup(ActionEvent event) {
        usedEmailLabel.setVisible(false);
//        getting the values
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String bio = bioField.getText();
        String picture = picturePath == "" ? "\"src/main/resources/com/example/feedy/images/twitter.png\"" : picturePath;

        //making sure that all the fields are filled
        EmailValidator emailValidator = new EmailValidator();
        if (name.trim().isEmpty()) {
            nameField.getStyleClass().add("error");
            return;
        } else {
            nameField.getStyleClass().remove("error");
        }
        if (email.trim().isEmpty() || !emailValidator.validate(email)) {
            emailField.getStyleClass().add("error");
            return;
        } else {
            emailField.getStyleClass().remove("error");
        }
        if (password.trim().isEmpty()) {
            passwordField.getStyleClass().add("error");
            return;
        } else {
            passwordField.getStyleClass().remove("error");
        }
        if (bio.trim().isEmpty()) {
            bioField.getStyleClass().add("error");
            return;
        } else {
            bioField.getStyleClass().remove("error");
        }

        int registerOperation = usersRepository.register(name, email, password, bio, picture);
        if (registerOperation != 0) {
            System.out.println("user registered successfully");
            redirectToHome();
        } else {
            System.out.println("user registration failed");
            emailField.getStyleClass().add("error");
            usedEmailLabel.setVisible(true);
        }
    }

    @FXML
    public void openLoginPage(MouseEvent event) throws IOException {
        System.out.println("opening login page");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("login_view.fxml"));
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

}
