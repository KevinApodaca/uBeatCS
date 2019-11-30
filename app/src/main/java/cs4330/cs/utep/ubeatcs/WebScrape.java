package cs4330.cs.utep.ubeatcs;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Isaias Leos
 */
public class WebScrape {

    private String url;

    WebScrape(String url) {
        this.url = url;
    }


    public void getInfo(String url) {
        String output = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            output = String.valueOf(doc.getElementsByClass("peopleSection"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        String name = null;
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            name = doc.title();
//            for (Element e : doc.getElementsByClass("profilePicture img-thumbnail")) {
//                if (doc.getElementById("profilePicture img-thumbnail").toString().contains(toCompare)) {
//                    Log.e("Image", e.attr("src"));
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!url.contains("gates")) {
            assert name != null;
            String[] fixedName = name.split(" ");
            name = fixedName[0] + " " + fixedName[1];
        }
        assert name != null;
        Log.e("Teacher", name);
        return name;
    }
}

//
//    String[] name = url.split("/");
//        Log.e("Professor", name[name.length - 1]);