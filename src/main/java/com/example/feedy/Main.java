package com.example.feedy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 700, 450);
        stage.setScene(loginScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}