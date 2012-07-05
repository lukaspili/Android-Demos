package com.siu.android.demos.gson;

import com.google.gson.*;
import com.siu.android.andutils.gson.CommonDeserializer;
import com.siu.android.andutils.model.DemoModel;

import java.lang.reflect.Type;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoModelDeserializer implements JsonDeserializer<DemoModel> {

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public DemoModel deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        DemoModel demoModel = new DemoModel();
        demoModel.setWebserviceId(CommonDeserializer.getString(jsonObject.get(ID)));
        demoModel.setName(CommonDeserializer.getString(jsonObject.get(NAME)));

        return demoModel;
    }
}
