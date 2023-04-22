package com.example.feedy;

public class Message {
    public int id;
    public User sender;
    public String message;
    public String date;
    public Message(int id, User sender_id, String message, String date) {
        this.id = id;
        this.sender = sender_id;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
