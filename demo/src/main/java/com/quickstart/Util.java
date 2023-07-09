package com.quickstart;

import com.google.gson.Gson;

public class Util {
    public static <T> T clone(T obj, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(obj), clazz);
    }

    public static <T> T clone(T obj) {
        Class<T> clazz = (Class<T>) obj.getClass();
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(obj), clazz);
    }
}
