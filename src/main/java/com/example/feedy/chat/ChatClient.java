package com.example.feedy.chat;

import com.example.feedy.AppState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("starting client!");
        try {
            Socket socket = new Socket("127.0.0.1", 9002);
            System.out.println("client is connected to socket");

            //the server will ask for the id
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int id = AppState.currentUser;
            //sending the ID to the server
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(id);
            pw.flush();

            //allowing the client to discuss
            new Writer(pw).start();
            new Reader(br).start();
        }catch (Exception e){
            System.out.println("client : an error has occurred");
        }
    }
}
