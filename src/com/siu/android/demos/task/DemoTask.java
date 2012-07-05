package com.siu.android.demos.task;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.siu.android.andutils.util.HttpUtils;
import com.siu.android.andutils.util.NetworkUtils;
import com.siu.android.demos.activity.DemoActivity;
import com.siu.android.demos.gson.DemoGsonContext;
import com.siu.android.demos.model.DemoModel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoTask extends AsyncTask<Void, Void, List<DemoModel>> {

    private DemoActivity activity;

    public DemoTask(DemoActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<DemoModel> doInBackground(Void... voids) {

        // if no network available, then stop
        if (!NetworkUtils.isOnline()) {
            return null;
        }

        // download data
        String data = HttpUtils.get("http://frequencesradio.heroku.com/api/radios");

        if (StringUtils.isEmpty(data)) {
            return null;
        }

        // parse it as list
        try {
            return DemoGsonContext.getInstance().getGson().fromJson(data, new TypeToken<List<DemoModel>>() {}.getType());
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error parsing json", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<DemoModel> demoModels) {
        if (null == activity) {
            return;
        }

        activity.onDemoTaskFinished(demoModels);
    }

    public void setActivity(DemoActivity activity) {
        this.activity = activity;
    }
}
