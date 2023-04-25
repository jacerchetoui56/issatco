package com.example.feedy.chat;

import java.io.PrintWriter;
import java.util.Scanner;

public class Writer extends Thread{
    PrintWriter pw;
    Writer(PrintWriter pw){
        this.pw = pw;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        super.run();
        while(true){
            try {
                pw.println(scanner.nextLine());
                pw.flush();
            } catch (Exception e) {
                System.out.println("an error while sending the message");
            }
        }
    }
}
