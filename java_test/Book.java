/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.niklas.bookmanagement.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

/**
 *
 * @author nikla
 */
public class Book {
    private final BigInteger isbn;
    private final String title;
    private final String subtitle;
    private final String publisher;
    private String authors;   //TODO
    private final int pages;
    private final String publishingDate;
    private final String description;
    private final String language;
    private final String imgLink;

    public Book(BigInteger isbn, String title, String subtitle, String publisher, String authors, int pages,
                String publishingDate, String description,String language,String imgLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.authors = authors;
        this.pages = pages;
        this.publishingDate = publishingDate;
        this.description = description;
        this.language = language;
        this.isbn = isbn;
        this.imgLink = imgLink;
    }

    public Book(JSONObject obj) throws JSONException {
    	this.title = obj.optString("title");
        this.subtitle = obj.optString("subtitle");
        this.publisher = obj.optString("publisher");
        this.authors = obj.optString("authors");
        this.pages = obj.optInt("pages");
        this.publishingDate = obj.optString("publishingDate");
        this.description = obj.optString("description");
        this.language = obj.optString("language");
        this.isbn = new BigInteger(obj.optString("isbn"));
        this.imgLink = obj.optString("imgLink");
    }

    public String getImgLink() {
        return imgLink;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthors() {
        return authors;
    }

    public int getPages() {
        return pages;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getIsbn() {
        return isbn.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Titel: %s%nAutor: %s%nSeitenzahl: %d", title,authors
                ,pages);
    }

    public JSONObject toJson(){
        JSONObject o = new JSONObject();
        try {
            o.putOpt("title",title);
            o.putOpt("subtitle",subtitle);
            o.putOpt("publisher",publisher);
            o.putOpt("authors",authors);
            o.put("pages",pages);
            o.putOpt("publishingDate",publishingDate);
            o.putOpt("description",description);
            o.putOpt("language",language);
            o.putOpt("isbn",isbn.toString());
            o.putOpt("imgLink",imgLink);
            //return o;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
}
