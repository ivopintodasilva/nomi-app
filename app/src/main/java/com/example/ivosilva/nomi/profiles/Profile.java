package com.example.ivosilva.nomi.profiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by silva on 24-10-2015.
 */
public class Profile {
    private int id;
    private String name;
    private ColorsEnum color;
    private List<Atributos> atributos;
    private HashMap<String, String> connections;

    public Profile(int id, String name, ColorsEnum color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.atributos = new ArrayList<>();
        this.connections = new HashMap<>();
    }


    public void addAttr(Atributos attr) { this.atributos.add(attr); }

    public List getAllAttr() { return this.atributos; }

    public void addConn(String key, String value) { this.connections.put(key, value); }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getColor() { return color.name(); }

    @Override
    public String toString() { return this.id + " - " + this.name; }

}
