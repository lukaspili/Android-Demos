package com.siu.android.demos.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.siu.android.andutils.util.CryptoUtils;
import com.siu.android.andutils.util.HttpUtils;
import com.siu.android.andutils.util.NetworkUtils;
import com.siu.android.demos.activity.DemoActivity;
import com.siu.android.demos.database.DatabaseHelper;
import com.siu.android.demos.gson.GsonHelper;
import com.siu.android.demos.model.DemoModel;
import com.siu.android.demos.model.DemoModelDao;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DemoTask extends AsyncTask<Void, Void, List<DemoModel>> {

    private DemoActivity activity;
    private SharedPreferences sharedPreferences;

    public DemoTask(DemoActivity activity) {
        this.activity = activity;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    @Override
    protected List<DemoModel> doInBackground(Void... voids) {

        // if no network available, then stop
        if (!NetworkUtils.isOnline()) {
            return getLocal();
        }

        // download data
        String data = HttpUtils.get("http://frequencesradio.heroku.com/api/radios");

        if (StringUtils.isEmpty(data)) {
            return getLocal();
        }

        // compare md5
        final String existingMd5 = sharedPreferences.getString("demomodel_md5", null);
        final String currentMd5 = CryptoUtils.md5Hex(data);

        if (StringUtils.equals(existingMd5, currentMd5)) {
            return getLocal();
        }

        final List<DemoModel> demoModels;

        // parse it as list
        try {
            demoModels = new GsonHelper().getGson().fromJson(data, new TypeToken<List<DemoModel>>() {}.getType());
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error parsing json", e);
            return null;
        }

        // remove old entries and replace by the new ones
        final DemoModelDao dao = DatabaseHelper.getInstance().getDaoSession().getDemoModelDao();

        DatabaseHelper.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {

                // remove all
                dao.deleteAll();

                // insert new
                for (Iterator<DemoModel> it = demoModels.iterator(); it.hasNext(); ) {
                    dao.insert(it.next());
                }

                // update md5
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("demomodel_md5", currentMd5);
                editor.commit();
            }
        });

        return demoModels;
    }

    private List<DemoModel> getLocal() {
        return DatabaseHelper.getInstance().getDaoSession().getDemoModelDao().loadAll();
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
