package com.example.ivosilva.nomi.contacts;

import java.util.HashMap;

/**
 * Created by ivosilva on 17/10/15.
 */
public class CollectedProfiles {
    private int id;
    private int photo_id;
    private String name;
    private HashMap<String, String> contacts;

    public CollectedProfiles(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addContact(String key, String value){
        contacts.put(key, value);
    }

    public HashMap<String, String> getAllContacts(){
        return contacts;
    }

    public String getContact(String key){
        return contacts.get(key);
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public int getPhotoId(){
        return this.photo_id;
    }

    public String toString(){
        return this.id + " - " + this.name;
    }


}