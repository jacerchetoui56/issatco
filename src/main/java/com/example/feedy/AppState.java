package com.example.feedy;


//this class is used to store the state of the application : current user , etc...

public class AppState {
    public static int currentUser = 0;
    public static int visitedUser = 0;
    public static int postToUpdate = 0;

    public static void stateLogin(int userId){
        currentUser = userId;
        System.out.println("State Change: user " + currentUser + " is now connected");
    }
    public static void stateLogout(){
        currentUser = 0;
    }
    public static void stateVisitedUser(int userId){
        visitedUser = userId;
        System.out.println("State Change: user " + visitedUser + " is now visited");
    }
    public static void statePostToUpdate(int postId){
        postToUpdate = postId;
        System.out.println("State Change: post " + postToUpdate + " is now updated");
    }
}
