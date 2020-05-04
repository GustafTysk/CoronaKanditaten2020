package com.example.coronakanditaten2020;

import java.util.ArrayList;

public class Post {
    String username;
    String title;
    String timestamp;
    String text;
    ArrayList <String> likes;
    String category;
    int id;
    int parentId;


    public Post(String username, String title, String timestamp, String text, ArrayList<String> likes, String category, int id, int parentId) {
        this.username = username;
        this.title = title;
        this.timestamp = timestamp;
        this.text = text;
        this.likes = likes;
        this.category = category;
        this.id = id;
        this.parentId = parentId;
    }

    public Post() {


    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public ArrayList<String> getLikes() {
        return likes;
    }
    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getParentId() {
        return parentId;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String printInformation(){
        return ("Username: "+this.username + "\n" + "title: " + this.title + "\n" + "Timestamp: " + this.timestamp + "\n" +"Text: " + this.text + "\n" + "Likes: " + this.likes + "\n" + "Category: " + this.category + "\n"
        + "Id: "+ this.id +"\n" + "ParentId: "+ "\n" + this.parentId);
    };
}
