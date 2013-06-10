package com.mns.walker.main;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.mns.walker.R;
import com.mns.walker.game.WalkerDefinition;
import com.mns.walker.game.WalkerActivity;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;

public class MainActivity extends ListActivity {

    public final static String WALK_ID = "com.mns.walker.WALK_ID";

    private static final String WALKS_ASSET_PATH = "walks";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isExternalStorageReadable() && isExternalStorageWritable()) {
            File walkerFiles = getWalkerFilesDir();
//            if (walkerFiles.listFiles().length == 0) {
                //unpack assets to directory
                copyAssets();
//            }
            setListAdapter(new CityArrayAdapter(this, walkerFiles.list()));
        }
    }

    private File getWalkerFilesDir() {
        return getExternalFilesDir(null);
    }

    private void copyAssets() {
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
                OutputStream out = new FileOutputStream(getWalkerFilesDir() + File.separator + filename);
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

        try {
            WalkerDefinition walkerDefinition = loadWalkDefinitionFromFileSystem(selectedValue);

            intent.putExtra(WALK_ID, walkerDefinition);
            startActivity(intent);

        } catch (IOException e) {
            Log.e(WalkerUtils.APPTAG, getResources().getString(R.string.sdcard_load_error), e);
            Toast.makeText(this, getResources().getString(R.string.sdcard_load_error), Toast.LENGTH_SHORT).show();
        } catch (XmlPullParserException e) {
            Log.e(WalkerUtils.APPTAG, getResources().getString(R.string.xml_error), e);
            Toast.makeText(this, getResources().getString(R.string.xml_error), Toast.LENGTH_SHORT).show();
        }

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


    // Uploads XML from stackoverflow.com, parses it, and combines it with
    // HTML markup. Returns HTML string.
    private WalkerDefinition loadWalkDefinitionFromFileSystem(String xmlResource) throws XmlPullParserException, IOException {
        InputStream stream = null;
        WalkerXmlParser walkerXmlParser = new WalkerXmlParser();
        WalkerDefinition walkerDefinition = null;

        try {
            stream = loadXmlFile(xmlResource);
            walkerDefinition = walkerXmlParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return walkerDefinition;
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream loadXmlFile(String xmlResource) throws IOException {
        // get a reference to the file.
        File file = new File(getWalkerFilesDir() + File.separator + xmlResource);
        // create an input stream to be read by the stream reader.
        FileInputStream fis = new FileInputStream(file);
        return fis;
    }

}