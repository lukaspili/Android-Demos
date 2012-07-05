package com.siu.android.demos.gson;

import com.google.gson.GsonBuilder;
import com.siu.android.andutils.gson.GsonContext;
import com.siu.android.demos.model.DemoModel;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoGsonContext extends GsonContext {

    @Override
    protected void configure(GsonBuilder builder) {
        builder.registerTypeAdapter(DemoModel.class, new DemoModelDeserializer());
    }
}
