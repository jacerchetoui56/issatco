package com.example.feedy.controllers;

import com.example.feedy.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    //fxml elements
    @FXML
    private Label loginLink;

    private Stage stage;
    private Scene scene;
    private Parent root;



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
}
