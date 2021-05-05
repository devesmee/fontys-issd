package com.example.issd_application;

import java.util.ArrayList;

public class FilterManager {

    //SINGLETON: create ONE-AND-ONLY object of LocalDatabase
    private static FilterManager instance = null;

    public final ArrayList<String> categories = new ArrayList<>();

    //SINGLETON: constructor private so this class cannot be instantiated from outside this class
    private FilterManager(){}

    //SINGLETON: get the ONE-AND-ONLY object available
    public static FilterManager singleton() {
        if (instance == null) {
            instance = new FilterManager();
        }
        return instance;
    }
}
