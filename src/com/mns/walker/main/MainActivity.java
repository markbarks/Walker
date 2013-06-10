package com.mns.walker.main;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mns.walker.game.WalkerActivity;

import java.io.*;

public class MainActivity extends ListActivity {

    public final static String WALK_ID = "com.mns.walker.WALK_ID";

    private static final String WALKS_ASSET_PATH = "walks";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isExternalStorageReadable() && isExternalStorageWritable()) {
            File walkerFiles = WalkerUtils.getWalkerFilesDir(this);
//            if (walkerFiles.listFiles().length == 0) {
            //unpack assets to directory
            initWalkerFilesDirFromAssets();
//            }
            setListAdapter(new WalkerDefinitionsArrayAdapter(this, walkerFiles.list()));
        }
    }

    private void initWalkerFilesDirFromAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list(WALKS_ASSET_PATH);
        } catch (IOException e) {
            Log.e(WalkerUtils.APPTAG, "Failed to get asset file list.", e);
        }
        assert files != null;
        for (String filename : files) {
            Log.d(WalkerUtils.APPTAG, "Copying asset file: " + filename);
            try {
                InputStream in = assetManager.open(WALKS_ASSET_PATH + File.separator + filename);
                OutputStream out = new FileOutputStream(WalkerUtils.getWalkerFilesDir(this) + File.separator + filename);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                Log.e(WalkerUtils.APPTAG, "Failed to copy asset file: " + filename, e);
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(this, WalkerActivity.class);

        intent.putExtra(WALK_ID, selectedValue);
        startActivity(intent);

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}