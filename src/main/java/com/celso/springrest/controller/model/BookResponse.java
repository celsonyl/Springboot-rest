package com.celso.springrest.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class BookResponse implements Serializable {

    private Long id;
    private String author;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    private Date launch_date;
    private Double price;
    private String title;

    public BookResponse() {
    }

    public BookResponse(Long id, String author, Date launch_date, Double price, String title) {
        this.id = id;
        this.author = author;
        this.launch_date = launch_date;
        this.price = price;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}