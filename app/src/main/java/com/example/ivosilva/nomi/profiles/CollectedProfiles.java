package com.example.ivosilva.nomi.profiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silva on 24-10-2015.
 */
public class CollectedProfiles {
//    private int id;
//    private String name;
    private List<Profile> profiles;

    public CollectedProfiles() {
//        this.id = id;
//        this.name = name;
        this.profiles = new ArrayList<>();
    }

    public void addProfile(Profile profile) { this.profiles.add(profile); }

    public Profile getProfile(int id) { return this.profiles.get(id); }

    public List<Profile> getAllProfiles() { return this.profiles; }

//    public int getId(){ return this.id; }

//    public String getName(){ return this.name; }

//    @Override
//    public String toString() { return this.id + " - " + this.name; }
}
