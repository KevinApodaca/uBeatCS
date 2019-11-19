package cs4330.cs.utep.ubeatcs;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebScrape {

    String url;

    public WebScrape(String url) {
        this.url = url;
    }

    public String getTitle() {
        String title = null;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("Teacher", title);
        return title;
    }
}
