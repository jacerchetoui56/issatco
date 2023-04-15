package com.example.feedy.controllers;

import com.example.feedy.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    //the items of the FXML login page
    @FXML
    private Label signupLink;

    private Stage stage;
    private Scene scene;
    private Parent root;



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
    public void login(ActionEvent event){
        System.out.println("login");
    }

}
