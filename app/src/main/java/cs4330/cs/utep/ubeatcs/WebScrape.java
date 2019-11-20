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


    public void getInfo(String url) {
        String output = "";
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            output = String.valueOf(doc.getElementsByClass("peopleSection"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.e("Output", output);
    }

    public String getName() {
//        String[] urlSplit = url.split("/");
//        String toCompare = urlSplit[urlSplit.length - 1];
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