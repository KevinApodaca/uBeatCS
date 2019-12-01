package cs4330.cs.utep.ubeatcs;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaias Leos
 */
public class WebScrape {
    private String index = "https://www.utep.edu/cs/people/index.html";

    WebScrape() {
    }

    public String getInfo(String tag, String filter) {
        List<String> names = new ArrayList<>();
        String[] temp1;
        String[] temp2;
        String[] temp3;
        String temp4;
        String docString;
        Document doc;
        try {
            doc = Jsoup.connect(index).get();
            docString = String.valueOf(doc.getElementsByClass(tag));
            temp1 = docString.split("\n");
            for (String s : temp1) {
                if (tag.equals("email")) {
//                    Log.e("Email", s);
                    temp2 = s.split("mailto:");
                    temp3 = temp2[1].split(">");
                    temp4 = temp3[0].substring(0, (temp3[0].length() - 1));
                    temp3[0] = temp4;
                } else if (tag.equals("name")) {
//                    Log.e("Name", s);
                    temp2 = s.split(">");
                    temp3 = temp2[1].split("<");
                } else {
//                    Log.e("Link", s);
                    temp2 = s.split("<a href=\"");
                    temp3 = temp2[1].split("\">");
                }
                names.add(temp3[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (filter) {
            case "18673":
            case "18674":
                return names.get(25);
            case "17767":
            case "18672":
                return names.get(16);
            case "16281":
            case "16332":
                return names.get(22);
            case "16335":
            case "16990":
            case "16301":
            case "18657":
                return names.get(4);
            case "17764":
            case "13967":
            case "16779":
                return names.get(24);
            case "12125":
            case "17845":
            case "18943":
                return names.get(10);
            case "15350":
            case "13371":
            case "11452":
            case "15351":
            case "15424":
                return names.get(6);
            case "18779":
            case "19056":
                return names.get(27);
            case "17881":
            case "13370":
            case "17196":
                return names.get(18);
            case "15459":
            case "16657":
            case "15977":
                return names.get(12);
            case "18608":
            case "17445":
                return names.get(15);
            case "11869":
            case "17842":
            case "18664":
                return names.get(7);
            case "14954":
            case "14955":
            case "13363":
            case "14952":
                return names.get(1);
            case "15460":
            case "17752":
            case "13968":
                return names.get(2);
            case "15143":
            case "19041":
                return names.get(3);
            case "19054":
            case "19055":
                return names.get(8);
            case "11870":
            case "11984":
            case "11454":
            case "12815":
            case "16576":
            case "18517":
                return names.get(9);
            case "18609":
            case "17757":
            case "14953":
                return names.get(11);
            case "11871":
            case "18603":
            case "14018":
                return names.get(17);
            case "15721":
            case "15722":
                return names.get(20);
            case "15194":
            case "17844":
            case "17458":
            case "18956":
                return names.get(14);
        }
        return names.get(-1);
    }
}