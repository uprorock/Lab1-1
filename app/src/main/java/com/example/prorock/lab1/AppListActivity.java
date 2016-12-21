package com.example.prorock.lab1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;


public class AppListActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener,
        SearchView.OnQueryTextListener {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    int listPosition;
    ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_app_list);

        listview = (ListView)findViewById(android.R.id.list);
        listview.setLongClickable(true);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                listPosition = pos;
                openContextMenu(listview);
                return true;
            }
        });

        registerForContextMenu(listview);
        packageManager = getPackageManager();

        //listview.setTextFilterEnabled(true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //super.onListItemClick(l, v, position, id);
                ApplicationInfo app = applist.get(position);
                try {
                    Intent intent = packageManager
                            .getLaunchIntentForPackage(app.packageName);

                    if (null != intent) {
                        startActivity(intent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(AppListActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(AppListActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        new LoadApplications().execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_item_applist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        String toastText = null;
        ClipboardManager clipboard;
        ClipData clip;
        ApplicationInfo app = applist.get(listPosition);
        switch(id){
            case R.id.menu_applist_copyName:
                toastText = getResources().getString(R.string.toast_copy_done)
                        + app.loadLabel(packageManager);
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(app.loadLabel(packageManager), app.loadLabel(packageManager));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_applist_copyPacketName:
                toastText = getResources().getString(R.string.toast_copy_done)
                        + app.packageName;
                clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText(app.packageName, app.packageName);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));

        Iterator<ApplicationInfo> appListIterator = applist.iterator();
        while (appListIterator.hasNext()) {
            String appLabel = appListIterator.next().loadLabel(packageManager).toString().toLowerCase();
            if (!appLabel.contains(newText.toLowerCase())) {
                appListIterator.remove();
            }
        }

        listadaptor = new ApplicationAdapter(AppListActivity.this,
                R.layout.list_row, applist);
        listview.setAdapter(listadaptor);
        return true;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(AppListActivity.this,
                    R.layout.list_row, applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            listview.setAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {

            progress = ProgressDialog.show(AppListActivity.this, null,
                    getResources().getString(R.string.progress_text));
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}