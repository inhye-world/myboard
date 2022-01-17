package edu.example.myboard.dto;

import java.sql.Timestamp;

public class Board {
    private String bid;
    private String bname;
    private String date;
    private String title;
    private String content;
    private int hit;
    private int bnum;
    private String file_name;
    private String file_save_name;
    private long file_size;
    private String file_download_uri;


    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getFile_download_uri() {
        return file_download_uri;
    }

    public void setFile_download_uri(String file_download_uri) {
        this.file_download_uri = file_download_uri;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_save_name() {
        return file_save_name;
    }

    public void setFile_save_name(String file_save_name) {
        this.file_save_name = file_save_name;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getBnum() {
        return bnum;
    }

    public void setBnum(int bnum) {
        this.bnum = bnum;
    }
}
