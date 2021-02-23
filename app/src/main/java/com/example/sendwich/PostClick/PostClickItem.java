package com.example.sendwich.PostClick;

public class PostClickItem {
    private String key;
    private String id;
    private String message;
    private int like;

    public PostClickItem() {

    }

    public PostClickItem(String key, String id, String message, int like){
        this.key = key;
        this.id = id;
        this.message = message;
        this.like = like;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
