package com.example.withdatabase.model;

public class Note {
    private String id;
    private String text;


    public Note(String id, String text){
        this.id = id;
        this.text = text;
    }


    public String getText(){
        return text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
