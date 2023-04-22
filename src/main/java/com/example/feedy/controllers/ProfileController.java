package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.User;
import com.example.feedy.Post;
import com.example.feedy.repositories.PostRepository;
import com.example.feedy.repositories.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label bioLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView imageview;
    @FXML
    private ScrollPane postScrollPane;
    PostRepository postRepository = new PostRepository();
    UsersRepository usersRepository = new UsersRepository();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        User user = usersRepository.getUser(AppState.visitedUser);

        //setting the labels
        usernameLabel.setText(user.username);
        bioLabel.setText(user.bio);
        File file = new File(user.profile_picture);
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageview.setImage(new Image(localUrl));

        //adding the posts to the profile
        intitiazePosts();
    }

    @FXML
    void backToHome(ActionEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("feeds_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) usernameLabel.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void intitiazePosts(){
        VBox allPostsContainer = new VBox();
        allPostsContainer.getStyleClass().add("all_posts_container");
        //making the items of the posts and adding them to the scroll pane
        List<Post> posts = postRepository.getUserPosts(AppState.visitedUser);

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
            Label dateLabel = new Label(formatDate(post.created_at));
            dateLabel.getStyleClass().add("post_date");
            writerContainer.getChildren().add(dateLabel);
            ownerContainer.getChildren().add(writerContainer);

            Label contentLabel = new Label(post.content);
            contentLabel.setWrapText(true);
            contentLabel.getStyleClass().add("post_content");

            postContainer.getChildren().addAll( ownerContainer, contentLabel);
            allPostsContainer.getChildren().add(postContainer);
        }
        postScrollPane.setContent(allPostsContainer);
        postScrollPane.setFitToWidth(true);


    }


    public static String formatDate(String d){
        // Parse the string to a Date object
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = null;
        try {
            date = inputDateFormat.parse(d);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Format the date to the desired string format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return outputDateFormat.format(date);
    }

}
