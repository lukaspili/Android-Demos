package com.siu.android.demos.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class GsonFormatter {

    private static Gson gson = new GsonBuilder()
            .create();

    public static Gson getGson() {
        return gson;
    }
}
