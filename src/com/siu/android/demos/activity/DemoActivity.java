package com.siu.android.demos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.siu.android.demos.R;
import com.siu.android.demos.task.DemoTask;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends Activity {

    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    private List<String> strings;
    private DemoTask demoTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // request indeterminate progress before setContentView
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        // progress is shown by default, so disable it
        setProgressBarIndeterminateVisibility(false);

        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.list);

        demoTask = (DemoTask) getLastNonConfigurationInstance();

        if (null == demoTask) {
            strings = new ArrayList<String>();
        } else {
            strings = (List<String>) savedInstanceState.get("list");
            demoTask.setActivity(this);
        }

        initList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (strings.isEmpty() && null == demoTask) {
            startDemoTask();
        }
    }

    private void initList() {
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        listView.setAdapter(listAdapter);
    }


    /* Rotation handling */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (ArrayList<String>) strings);
    }

    @Override
    public Object getLastNonConfigurationInstance() {

        if (null == demoTask) {
            return null;
        }

        // if demo task is running
        // remove old activity that will be destroyed
        demoTask.setActivity(null);

        // and return the demotask as a saved object during rotation
        return demoTask;
    }


    /* Demo task */

    private void startDemoTask() {
        stopDemoTaskIfRunning();

        setProgressBarIndeterminateVisibility(true);
        demoTask = new DemoTask(this);
        demoTask.execute();
    }

    private void stopDemoTaskIfRunning() {
        if (null == demoTask) {
            return;
        }

        if (!demoTask.isCancelled()) {
            demoTask.cancel(true);
        }

        setProgressBarIndeterminateVisibility(false);
        demoTask = null;
    }

    public void onDemoTaskFinished(List<String> stringsLoaded) {
        stopDemoTaskIfRunning();

        if (null == stringsLoaded) {
            Toast.makeText(this, "Impossible de récupérer les informations du flux", Toast.LENGTH_LONG).show();
            return;
        }

        if (stringsLoaded.isEmpty()) {
            Toast.makeText(this, "Le flux est vide", Toast.LENGTH_LONG).show();
            return;
        }

        // clear previous content from list
        strings.clear();

        // and add the new loaded content
        strings.addAll(stringsLoaded);

        // notifiy list adapter that content changed
        listAdapter.notifyDataSetChanged();
    }

}
