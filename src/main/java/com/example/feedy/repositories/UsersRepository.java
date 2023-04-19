package com.example.feedy.repositories;

import com.example.feedy.AppState;
import com.example.feedy.MyConnexion;

import java.sql.*;

public class UsersRepository {
    Connection connection = null;
    Statement statement = null;

    public UsersRepository() {
        connection = MyConnexion.connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //the login function will return a boolean value
    public boolean login(String email, String password) {
        if (connection != null) {
            String requete = "select * from users where email=? and password=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    System.out.println("user found");
                    //getting the user id from the database
                    int id = result.getInt("id");
                    AppState.stateLogin(id);

                    return true;
                } else {
                    System.out.println("user not found");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("error while login : " + e.getMessage());
            }
        }
        return false;
    }

    public int register(String username, String email, String password, String profile_picture, String bio) {
        if (connection != null) {
            String requete = "insert into users(username, email, password, profile_picture, bio) values(?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, profile_picture);
                preparedStatement.setString(5, bio);
                System.out.println(username + " created with success");
                int result = preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                //getting the user id
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    AppState.stateLogin(id);
                }

                return result;
            } catch (SQLException e) {
                System.out.println("error while creating user : " + e.getMessage());
            }
        }
        return 0;
    }

    public int deleteUser(int id) {
        if (connection != null) {
            String requete = "delete from users where id=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setInt(1, id);
                System.out.println("User deleted");
                return preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("error while deleting user : " + e.getMessage());
            }
        }
        return 0;
    }

    public ResultSet getUser(String email) {
        if (connection != null) {
            String requete = "select * from users where email=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, email);
                System.out.println("User found");
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                System.out.println("error while finding user : " + e.getMessage());
            }
        }
        return null;
    }

    public ResultSet searchUser(String username) {
        if (connection != null) {
            //it is enough if the name starts with the given string
            String requete = "select * from users where username like ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, username + "%");
                System.out.println("User found");
                return preparedStatement.executeQuery();
            } catch (SQLException e) {
                System.out.println("error while searching user : " + e.getMessage());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //todo:this function is for testing purposes
        UsersRepository usersRepository = new UsersRepository();

        //----------creating a user
//        usersRepository.register("souhail", "souhailabdallah@gmail.com", "souhailpass", "https://www.google.com", "I am Souhail and I am Jacer's friend");

        //----------searching a user by username
        /*ResultSet resultSet = usersRepository.searchUser("jac");
        try {
            while (resultSet.next()) {
                //print all the information if the user
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("email"));
                System.out.println(resultSet.getString("profile_picture"));
                System.out.println(resultSet.getString("bio"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        //----------getting the information of a user by id
//        ResultSet resultSet = usersRepository.getUser(114);
//         try {
//             while (resultSet.next()) {
//                 //print all the information if the user
//                 System.out.println(resultSet.getString("username"));
//                 System.out.println(resultSet.getString("email"));
//                 System.out.println(resultSet.getString("profile_picture"));
//                 System.out.println(resultSet.getString("bio"));
//             }
//
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
    }

}



// todo:
// - add the date of birth , gender



