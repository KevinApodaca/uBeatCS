package cs4330.cs.utep.ubeatcs;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Class scrapes the web using JSoup and is used for scraping the image of the instructor, along with their email, as well as letting the user select which teacher's course to load.
 *
 * @author Kevin Apodaca
 */
public class WebScrape {

    String url;

    public WebScrape(String url) {
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
            String[] fixedName = name.split(" ");
            name = fixedName[0] + " " + fixedName[1];
        }
        Log.e("Teacher", name);
        return name;
    }
}

//
//    String[] name = url.split("/");
//        Log.e("Professor", name[name.length - 1]);