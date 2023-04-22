package com.example.feedy.controllers;

import com.example.feedy.AppState;
import com.example.feedy.Main;
import com.example.feedy.Message;
import com.example.feedy.repositories.MessagesRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GroupChatController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ScrollPane messagesScrollPane;
    @FXML
    private TextField input_field;
    MessagesRepository messagesRepository = new MessagesRepository();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        initializeMessages();
        input_field.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                sendMessage(null);
            }
        });
    }


    @FXML
    void backToHome(MouseEvent event) {
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("feeds_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) messagesScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initializeMessages() {
        VBox allMessagesContainer = new VBox();
        allMessagesContainer.getStyleClass().add("all_messages_container");

        MessagesRepository messagesRepository = new MessagesRepository();
        List<Message> messages = messagesRepository.getAllMessages();
        for (Message message : messages) {
            HBox messageBlock = new HBox();
            messageBlock.getStyleClass().add("message_block");
            HBox.setMargin(messageBlock, new Insets(0, 0, 10, 0));
            if (message.sender.id == AppState.currentUser) {
                messageBlock.setAlignment(Pos.CENTER_RIGHT);
            } else {
                messageBlock.setAlignment(Pos.CENTER_LEFT);
            }
            VBox messageContainer = new VBox();
            HBox header = new HBox();
            Label date = new Label(ProfileController.formatDate(message.date));
            Label senderLabel = new Label(message.sender.username);
            senderLabel.setOnMouseClicked(event -> openProfile(message.sender.id));
            header.getChildren().addAll(senderLabel, date);
            Text messageLabel = new Text(message.message);
            messageLabel.setFont(Font.font("System Regular", 15));
            //setting the color
            messageLabel.setFill(AppState.currentUser == message.sender.id ? Color.rgb(255,255,255) : Color.rgb(0, 0, 0));
            messageLabel.wrappingWidthProperty().bind(allMessagesContainer.widthProperty().subtract(200));

// adding classes depending on the user
            messageContainer.getStyleClass().add(AppState.currentUser == message.sender.id ? "message_container_sender" : "message_container_receiver");
            senderLabel.getStyleClass().add(AppState.currentUser == message.sender.id ? "sender" : "receiver");
            messageLabel.getStyleClass().add(AppState.currentUser == message.sender.id ? "message_sender" : "message_receiver");
            date.getStyleClass().add(AppState.currentUser == message.sender.id ? "date_sender" : "date_receiver");

            messageContainer.getChildren().addAll(header, messageLabel);
//            messageContainer.maxWidthProperty().bind(messageBlock.widthProperty().multiply(0.7));
            messageBlock.getChildren().add(messageContainer);
            allMessagesContainer.getChildren().add(messageBlock);
        }
        messagesScrollPane.setFitToWidth(true);
        messagesScrollPane.setFitToHeight(true);
        messagesScrollPane.setContent(allMessagesContainer);
        messagesScrollPane.setVvalue(1.0);
    }

    public void openProfile(int id) {
        AppState.stateVisitedUser(id);
        try {
            // Load the home view FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("profile_view.fxml"));
            root = loader.load();
            // Create a new scene and set it on the stage
            Scene homeViewScene = new Scene(root);
            Stage currentStage = (Stage) messagesScrollPane.getScene().getWindow(); // get the current stage
            currentStage.setScene(homeViewScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void sendMessage(ActionEvent event) {
        String text = input_field.getText().trim();
        if (text.isEmpty()) {
            return;
        }
        messagesRepository.sendMessage(AppState.currentUser, input_field.getText());
        input_field.setText("");
        initializeMessages();
    }
}



