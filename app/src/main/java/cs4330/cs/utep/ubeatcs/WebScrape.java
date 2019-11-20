package cs4330.cs.utep.ubeatcs;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

public class WebScrape {

    String url;

    public WebScrape(String url) {
        this.url = url;
    }

    public String getTitle() {
        String[] urlSplit = url.split("/");
        String toCompare = urlSplit[urlSplit.length - 1];
        String title = null;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            title = doc.title();
            for (Element e : doc.getElementsByClass("profilePicture img-thumbnail")) {
                Log.e("Image", e.attr("src"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!url.contains("Dr.")) {
            String[] name = title.split(" ");
            title = name[0] + " " + name[1];
        }
        Log.e("Teacher", title);
        return title;
    }
}

//
//    String[] name = url.split("/");
//        Log.e("Professor", name[name.length - 1]);