package com.example.ivosilva.nomi.profiles;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by silva on 24-10-2015.
 */
public class Profile {
    private int id;
    private String name;
    private String color;
    private HashMap<String, String> atributos;
//    private List<String> connections;

    public Profile(int id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.atributos = new HashMap<>();
//        this.connections = new LinkedList<>();
    }


    public void addAttr(String attr, String value) { this.atributos.put(attr,value); }

    public HashMap<String, String> getAllAttr() { return this.atributos; }

//    public void addConn(String value) { this.connections.add(value); }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getColor() { return color; }

    @Override
    public String toString() { return this.id + " - " + this.name; }

}
