package com.nulp.yuriiukrainets.shedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InstGroupSelector {
    //Declaring progress bar
    public ProgressBar progressBar;
    public Context context;

    InstGroupSelector(Context context, ProgressBar progressBar){
        this.progressBar = progressBar;
        this.context = context;
    }

    public List<String> getFromSiteBySelector(String URL, String cssSelector) throws ExecutionException, InterruptedException {
        return new InstParser().execute(URL, cssSelector).get();
    }

    @SuppressLint("StaticFieldLeak")
    private class InstParser extends AsyncTask<String, Void, List<String>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();
        }

        @Override
        protected List<String> doInBackground(String... url) {
            Document doc = null;
            String fullURL = url[0];
            String cssSelector = url[1];
            try {
                doc = Jsoup.connect(fullURL).timeout(0).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(doc != null) {
                Elements elements = doc.select(cssSelector);
                Elements options = elements.select("option");
                System.out.println(options.text());
                return Arrays.asList(options.text().substring(8).split(" "));
            } else {
                System.out.println("Parsed null!");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            progressBar.clearAnimation();
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

}
