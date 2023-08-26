package com.example.events;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {
    private String title;
    private String details;
    private String date;
    private int id;

    public Event(String title, String details, String date, int id) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.id = id;
    }

    public static ArrayList<Event> parseList(Cursor cursor) {
        ArrayList<Event> result = new ArrayList<>();
        if (!cursor.moveToFirst()) return null;
        do {
            Event event = new Event(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(0));
            result.add(event);
        } while (cursor.moveToNext());

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
