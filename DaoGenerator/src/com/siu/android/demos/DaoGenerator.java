package com.siu.android.demos;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DaoGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "com.siu.android.demos.model");
        schema.enableKeepSectionsByDefault();

        Entity demoModel = schema.addEntity("DemoModel");
        demoModel.implementsSerializable();
        demoModel.addIdProperty();
        demoModel.addStringProperty("webserviceId");
        demoModel.addStringProperty("name");

        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "./src-gen");
    }
}
