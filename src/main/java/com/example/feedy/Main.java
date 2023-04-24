package com.example.feedy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 700, 450);
        stage.setTitle("IssatCo");
        stage.setResizable(false);

        //add an icon to the window
        stage.setIconified(true);
        File file = new File("src/main/resources/com/example/feedy/images/twitter.png");
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image(localUrl);
        stage.getIcons().add(image);
        stage.setScene(loginScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}