package com.example.ivosilva.nomi.contacts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by ivosilva on 18/10/15.
 */
public class CollectedContactsSerializer implements JsonSerializer<CollectedContacts> {
    @Override
    public JsonElement serialize(CollectedContacts profile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("id", new JsonPrimitive(profile.getId()));
        result.add("name", new JsonPrimitive(profile.getName()));
        result.add("photo_id", new JsonPrimitive(profile.getPhotoId()));
        return result;
    }
}
