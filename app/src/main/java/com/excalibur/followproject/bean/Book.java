package com.excalibur.followproject.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class Book extends DataSupport implements Serializable{

    private int bid;
    private int id;
    private String name;
    private String url;
    private String img;
    private String description;
    private int status;
    private String type;
    private String author;
    private int textNumber;
    private int readNumber;
    private int lastUpdate;
    private String download;
    private String indexUrl;
    private int todayUpdate;
    private String tags;
    private String newZhangJie;

    private int leixing;
    private String lastRead;
    private int pageNumber;
    private long addTime;
    private long lastReadTime;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void setLeixing(int leixing) {
        this.leixing = leixing;
    }

    public int getLeixing() {
        return leixing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTextNumber() {
        return textNumber;
    }

    public void setTextNumber(int textNumber) {
        this.textNumber = textNumber;
    }

    public int getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(int readNumber) {
        this.readNumber = readNumber;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public int getTodayUpdate() {
        return todayUpdate;
    }

    public void setTodayUpdate(int todayUpdate) {
        this.todayUpdate = todayUpdate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNewZhangJie() {
        return newZhangJie;
    }

    public void setNewZhangJie(String newZhangJie) {
        this.newZhangJie = newZhangJie;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
    }
}
