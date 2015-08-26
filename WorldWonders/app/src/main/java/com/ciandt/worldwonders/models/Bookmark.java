package com.ciandt.worldwonders.models;

import java.io.Serializable;


public class Bookmark implements Serializable {

    public int id;
    public int idWonders;

    public Bookmark(int idWonders) {
        this.idWonders = idWonders;
    }

    public Bookmark() {
    }

}