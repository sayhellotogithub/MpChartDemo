package com.iblogstreet.mpchartdemo.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 @author junwang
 @aate 2020/9/28 4:40 PM
 @desc 
**/
public class MockUtil {

    public static <T> T getModel(String json, Class<T> tClass) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }

    public static <T> List<T> getModelList(String json, Class<T[]> tClass) {
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        T[] list = gson.fromJson(json, tClass);
        if (list == null) {
            return new ArrayList<>();
        }
        List<T> ts = Arrays.asList(list);
        return new ArrayList<>(ts);
    }

    public static <T> T getModel(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
