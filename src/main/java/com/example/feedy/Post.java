package com.example.feedy;


//this class is for the single post object
public class Post {
    public int id;
    public String content;
    public User user;
    public String created_at;

    public Post(int id, String content, User user, String created_at){
        this.id = id;
        this.content = content;
        this.user = user;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}

