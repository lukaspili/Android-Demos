package com.siu.android.demos.gson;

import com.google.gson.GsonBuilder;
import com.siu.android.andutils.gson.GsonOpenHelper;
import com.siu.android.demos.model.DemoModel;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class GsonHelper extends GsonOpenHelper {

    @Override
    protected void configure(GsonBuilder builder) {
        builder.registerTypeAdapter(DemoModel.class, new DemoModelDeserializer());
    }
}
