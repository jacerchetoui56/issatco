package com.example.feedy.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader extends Thread{
    BufferedReader br;

    Reader(BufferedReader br){
        this.br = br;
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                System.out.println("I received a message, adding it to the list of messages");
                System.out.println(br.readLine());
            } catch (IOException e) {
                System.out.println("an error while reading the message");
                //Stopping the thread if it fails
                break;
            }
        }
    }
}
