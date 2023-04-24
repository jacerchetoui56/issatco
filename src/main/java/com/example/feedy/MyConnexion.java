package com.example.feedy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnexion {
    public static Connection connect() {
        String nomDriver = "com.mysql.jdbc.Driver";
        Connection connection = null;
        try {
            Class.forName(nomDriver);
        } catch (ClassNotFoundException e) {
            System.out.println("erreur: " + e.getMessage());
        }

        String url = "jdbc:mysql://localhost/issatco";
        String user = "root";
        String mp = "";
        try {
            connection = DriverManager.getConnection(url, user, mp);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("erreur de connection :" + e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        MyConnexion.connect();
    }
}
