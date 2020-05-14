package com.example.coronakanditaten2020;

import java.util.ArrayList;
import java.util.Comparator;

public class Post implements Comparable<Post> {
    String username;
    String email;
    String title;
    String timestamp;
    String text;
    int likes;
    String category;
    int id;
    int parentId;


    public Post(String username, String email, String title, String timestamp, String text, int likes, String category, int parentId) {
        this.username = username;
        this.email = email;
        this.title = title;
        this.timestamp = timestamp;
        this.text = text;
        this.likes = likes;
        this.category = category;
        this.parentId = parentId;
    }


    public Post(String username, String email, String title, String timestamp, String text, int likes, String category, int id, int parentId) {
        this.username = username;
        this.email = email;
        this.title = title;
        this.timestamp = timestamp;
        this.text = text;
        this.likes = likes;
        this.category = category;
        this.id = id;
        this.parentId = parentId;
    }

    public static final Comparator<Post> DESCENDING_COMPARATOR = new Comparator<Post>() {
        // Overriding the compare method to sort the age
        public int compare(Post p1, Post p2) {
           // return p1.likes - p2.likes;
            return p2.likes - p1.likes;
        }
    };

    public int compareTo(Post p) {
        return (this.title).compareTo(p.title);
    }

//    public int compare( d, Dog d1) {
//        return DESCENDING_COMPARATOR.compare(d, d1);
//    }

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
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String printInformation(){
        return ("Username: "+this.username + "\n" + "title: " + this.title + "\n" + "Timestamp: " + this.timestamp + "\n" +"Text: " + this.text + "\n" + "Likes: " + this.likes + "\n" + "Category: " + this.category + "\n"
        + "Id: "+ this.id +"\n" + "ParentId: "+ "\n" + this.parentId);
    };
}
