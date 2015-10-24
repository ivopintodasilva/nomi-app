package com.example.ivosilva.nomi.profiles;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by silva on 24-10-2015.
 */
public class ProfilesSerializer implements JsonSerializer<Profile> {
    @Override
    public JsonElement serialize(Profile profile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(profile.getId()));
        result.add("name", new JsonPrimitive(profile.getName()));
        result.add("color", new JsonPrimitive(profile.getColor()));

        return result;
    }
}
