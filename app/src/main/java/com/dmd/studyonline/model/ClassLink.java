package com.dmd.studyonline.model;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.io.Serializable;

public class ClassLink implements Serializable {
    private String link;
    private String title;
    private String time = "chưa xác định";
    private String nameTeacher = "none";
    private String noteShort = "none";
    private int image = 0 ;

    public ClassLink( String title,String link) {
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public void UpImage(){
        image ++;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public String getNoteShort() {
        return noteShort;
    }

    public void setNoteShort(String noteShort) {
        this.noteShort = noteShort;
    }
}
