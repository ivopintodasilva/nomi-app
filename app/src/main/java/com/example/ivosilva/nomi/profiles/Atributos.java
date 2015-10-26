package com.example.ivosilva.nomi.profiles;

/**
 * Created by silva on 24-10-2015.
 */
public class Atributos {
    private int id;
    private Enum name;
    private String value;

    public Atributos(int id, Enum name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() { return id; }

    public String getName() { return name.name(); }

    public String getValue() { return value; }
}
