package com.example.ermolaenkoalex.NYTimes.model;

import java.io.Serializable;

public class Category implements Serializable {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
