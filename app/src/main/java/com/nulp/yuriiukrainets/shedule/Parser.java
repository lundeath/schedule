package com.nulp.yuriiukrainets.shedule;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Parser {

    public static void DownloadFromUrl(Context context, String fileName) throws IOException {
        //this is the downloader method

        try {
            URL url = new URL("http://lp.edu.ua/students_schedule?institutecode_selective="+
                    MainActivity.currentInst+
                    "&edugrupabr_selective="+
                    MainActivity.currentGroup) ;//you can write here any link
            File file = new File(context.getFilesDir()+fileName);

            long startTime = System.currentTimeMillis();
            Log.d("Parser", "download begining");
            Log.d("Parser", "download url:" + url);
            Log.d("Parser", "downloaded file dir:" + context.getFilesDir()+fileName);
            /* Open a connection to that URL. */
            URLConnection urlConnection = url.openConnection();

            /*
             * Define InputStreams to read from the URLConnection.
             */
            InputStream is = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            /*
             * Read bytes to the Buffer until there is nothing more to read(-1).
             */
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.close();
            Log.d("Parser", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");

        } catch (IOException e) {
            Log.d("Parser", "Error: " + e);
        }

    }
}
