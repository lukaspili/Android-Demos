package com.siu.android.demos.model;

import java.io.Serializable;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoModel implements Serializable {

    private String webserviceId;
    private String name;

    public String getWebserviceId() {
        return webserviceId;
    }

    public void setWebserviceId(String webserviceId) {
        this.webserviceId = webserviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
