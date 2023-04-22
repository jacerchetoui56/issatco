package com.example.feedy.repositories;

import com.example.feedy.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesRepository {
    Connection connection = null;
    Statement statement = null;

    public MessagesRepository() {
        connection = MyConnexion.connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int sendMessage(int sender_id, String messageText){
        if (connection != null) {
            String requete = "insert into messages(sender_id, message) values(?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, sender_id);
                preparedStatement.setString(2, messageText);
                System.out.println("message sent with success");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while sending message : " + e.getMessage());
            }
        }
        return 0;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        if (connection != null) {
            //making sure to not show the posts of the current user
            String requete = "SELECT messages.*, users.username, users.profile_picture FROM messages INNER JOIN users ON messages.sender_id = users.id ORDER BY messages.created_at ASC";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);

                ResultSet result = preparedStatement.executeQuery();
                while (result.next()){
                    int id = result.getInt("id");
                    int sender_id = result.getInt("sender_id");
                    String text = result.getString("message");
                    String username = result.getString("username");
                    String profile_picture = result.getString("profile_picture");
                    String created_at = result.getString("created_at");
                    User user = new User(sender_id, username, profile_picture);
                    Message message = new Message(id, user, text, created_at);
                    messages.add(message);
                }
            } catch (SQLException e) {
                System.out.println("error while getting all posts : " + e.getMessage());
            }
        }
        //returning an empty list if there is no connection
        return messages;
    }


    public static void main(String[] args) {
        MessagesRepository messagesRepository = new MessagesRepository();
        //sending a bunch of messages
//        messagesRepository.sendMessage(115, "hello one");
//        messagesRepository.sendMessage(114, "hello two");
//        messagesRepository.sendMessage(117, "hello three");
//        messagesRepository.sendMessage(116, "hello four");


        //getting all messages
        List<Message> messages = messagesRepository.getAllMessages();
        for (Message message : messages){
            System.out.println(message);
        }
    }

}
