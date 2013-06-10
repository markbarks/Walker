package com.mns.walker.main;

import android.content.Context;

import java.io.File;

public class WalkerUtils {

    /*
     * A log tag for the application
     */
    public static final String APPTAG = "Walker";

    // returns the path to files folder inside Android/data/data/com.mns.walker/ on the SD card
    public static File getWalkerFilesDir(Context context) {
        return context.getExternalFilesDir(null);
    }
}
