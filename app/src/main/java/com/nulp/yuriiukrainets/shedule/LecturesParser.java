package com.nulp.yuriiukrainets.shedule;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LecturesParser extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url[0]).timeout(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doc != null) {
            Elements elements = doc.select(".view-content");
            System.out.println(elements.text());
        } else {
            System.out.println("Parsed null!");
        }
        return null;
    }
}
