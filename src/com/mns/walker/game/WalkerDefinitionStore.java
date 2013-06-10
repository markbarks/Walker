package com.mns.walker.game;

import android.content.Context;
import com.mns.walker.main.WalkerUtils;
import com.mns.walker.main.WalkerXmlParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WalkerDefinitionStore {

    private final Context context;

    public WalkerDefinitionStore(Context context) {
        this.context = context;
    }

    // Loads XML from file system, parses it and returns WalkerDefinition
    public WalkerDefinition loadWalkDefinitionFromFileSystem(String xmlResource)
            throws XmlPullParserException, IOException {

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
        File file = new File(WalkerUtils.getWalkerFilesDir(context) + File.separator + xmlResource);
        // create an input stream to be read by the stream reader.
        FileInputStream fis = new FileInputStream(file);
        return fis;
    }

}
