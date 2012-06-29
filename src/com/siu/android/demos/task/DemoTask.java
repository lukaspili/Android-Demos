package com.siu.android.demos.task;

import android.os.AsyncTask;
import com.google.gson.reflect.TypeToken;
import com.siu.android.demos.activity.DemoActivity;
import com.siu.android.demos.gson.GsonFormatter;
import com.siu.android.demos.util.NetworkUtils;
import com.siu.android.demos.util.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoTask extends AsyncTask<Void, Void, List<String>> {

    private DemoActivity activity;

    public DemoTask(DemoActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {

        // if no network available, then stop
        if (!NetworkUtils.isOnline()) {
            return null;
        }

        // download data
        String data = UrlUtils.downloadData("foobar");

        if (StringUtils.isEmpty(data)) {
            return null;
        }

        // parse it as list
        return GsonFormatter.getGson().fromJson(data, new TypeToken<List<String>>() {
        }.getType());
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        if (null == activity) {
            return;
        }

        activity.onDemoTaskFinished(strings);
    }

    public void setActivity(DemoActivity activity) {
        this.activity = activity;
    }
}
