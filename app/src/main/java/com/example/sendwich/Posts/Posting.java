package com.example.sendwich.Posts;

import java.util.HashMap;
import java.util.Map;

public class Posting {

    private String ID;
    private String Name;
    private String Text;
    private String Time;
    private String PicNum;
    private int Like;

    public Posting() {

    }

    public Posting(String ID, String Name, String Text, String Time, String PicNum, int Like) {
        this.ID = ID;
        this.Name = Name;
        this.Text = Text;
        this.Time = Time;
        this.PicNum = PicNum;
        this.Like = Like;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getText() {
        return Text;
    }

    public void setText() {
        Text = Text;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPicNum() {
        return PicNum;
    }

    public void setPicNum(String picNum) {
        PicNum = picNum;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", ID);
        result.put("name", Name);
        result.put("text",Text);
        result.put("time", Time);
        result.put("picnum", PicNum);
        result.put("like", Like);
        return result;
    }
}
