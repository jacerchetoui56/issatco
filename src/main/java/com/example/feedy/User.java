package com.example.feedy;

public class User {
    public int id;
    public String username;
    public String bio;
    public String profile_picture;
    public User(int id, String username, String bio, String profile_picture){
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profile_picture = profile_picture;
    }
    public User(int id, String username, String profile_picture){
        this.id = id;
        this.username = username;
        this.profile_picture = profile_picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                '}';
    }
}
