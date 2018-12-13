/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.niklas.bookmanagement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

/**
 *
 * @author nikla
 */
public class Book {
    private final String isbn;
    private final String title;
    private final String subtitle;
    private final String publisher;
    private String authors;   //TODO
    private final int pages;
    private final String publishingDate;
    private final String description;
    private final String language;
    private final String imgLink;

    public Book(String isbn, String title, String subtitle, String publisher, String authors, int pages,
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

    public void setIsbn(String isbn)
    {

    }

    public Book(JSONObject obj) throws JSONException {
            this.title = obj.optString("title");
            this.subtitle = obj.optString("subtitle");;
            this.publisher = obj.optString("publisher");;
            this.authors = obj.optJSONArray("authors").toString();;
            this.pages = obj.optInt("pageCount");;
            this.publishingDate = obj.optString("publishedDate");;
            this.description = obj.optString("description");;
            this.language = obj.optString("language");;
            JSONArray isbnArray = obj.optJSONArray("industryIdentifiers");
            String x = null;
            if(isbnArray != null) {
                for (int i = 0; i < isbnArray.length(); i++) {
                    if(isbnArray.optJSONObject(i).optString("type").contentEquals("ISBN_13"))
                    {
                        x = isbnArray.optJSONObject(i).optString("identifier");
                    }
                }
            }
            if (x == null) {
                x = isbnArray.optJSONObject(0).optString("identifier");
            }
            this.isbn = x;
            JSONObject iL = obj.optJSONObject("imageLinks");
            if(iL == null)
                this.imgLink = "";
            else
                this.imgLink = iL.optString("thumbnail");
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
        return isbn;
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
            o.putOpt("isbn",isbn);
            o.putOpt("imgLink",imgLink);
            //return o;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
}
