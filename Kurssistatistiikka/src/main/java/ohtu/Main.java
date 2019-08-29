package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println(bodyText);
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

        String url2 = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String bodyText2 = Request.Get(url2).execute().returnContent().asString();
//        System.out.println(bodyText2);
        Course[] courses = mapper.fromJson(bodyText2, Course[].class);

        HashMap<String, ArrayList<Submission>> subMap = new HashMap<>();
        for (Submission sub : subs) {
            subMap.putIfAbsent(sub.getCourse(), new ArrayList<>());
            subMap.get(sub.getCourse()).add(sub);
        }

        String url3 = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String statsResponse = Request.Get(url3).execute().returnContent().asString();

        JsonParser parser = new JsonParser();
        JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
        Iterator<String> keyIter = parsedData.keySet().iterator();
        JsonArray jsArr = new JsonArray();
        while (keyIter.hasNext()) {
            jsArr.add(parsedData.get(keyIter.next()));
        }

        CourseStats[] stats = mapper.fromJson(jsArr, CourseStats[].class);
        int studTotal = 0;
        int exerTotal = 0;
        int hrTotal = 0;
        for (int i = 0; i < stats.length; i++) {
            studTotal += stats[i].getStudents();
            exerTotal += stats[i].getExercise_total();
            hrTotal += (int) stats[i].getHour_total();
        }

        HashMap<String, Integer[]> statsMap = new HashMap<>();
        statsMap.put("ohtu2018", new Integer[]{studTotal, exerTotal, hrTotal});

        String url4 = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        String statsResponse2 = Request.Get(url4).execute().returnContent().asString();

        JsonObject parsedData2 = parser.parse(statsResponse2).getAsJsonObject();
        Iterator<String> keyIter2 = parsedData2.keySet().iterator();
        JsonArray jsArr2 = new JsonArray();
        while (keyIter2.hasNext()) {
            jsArr2.add(parsedData2.get(keyIter2.next()));
        }

        CourseStats[] stats2 = mapper.fromJson(jsArr2, CourseStats[].class);
        int studTotal2 = 0;
        int exerTotal2 = 0;
        int hrTotal2 = 0;
        for (int i = 0; i < stats2.length; i++) {
            studTotal2 += stats2[i].getStudents();
            exerTotal2 += stats2[i].getExercise_total();
            hrTotal2 += (int) stats2[i].getHour_total();
        }

        statsMap.put("rails2018", new Integer[]{studTotal2, exerTotal2, hrTotal2});

        System.out.println("opiskelijanumero: " + studentNr + "\n");
        for (int i = 0; i < courses.length; i++) {
            ArrayList<Submission> mySubs = subMap.getOrDefault(courses[i].getName(), new ArrayList<>());
            if (mySubs.size() > 0) {
                System.out.println(courses[i].getFullName() + " " + courses[i].getTerm() + " " + courses[i].getYear() + "\n");
                int sumExer = 0;
                int maxExer = 0;
                int sumHrs = 0;
                for (int j = 0; j < mySubs.size(); j++) {
                    Submission sub = mySubs.get(j);
                    System.out.println(sub);
                    System.out.println("tehtyjä tehtäviä yhteensä " + sub.getExercises().size() + "/" + courses[i].getExercises().get(sub.getWeek()) + " aikaa kului " + sub.getHours() + " tuntia tehdyt tehtävät: " + sub.getExercises().toString());
                    sumExer += sub.getExercises().size();
                    sumHrs += sub.getHours();
                    maxExer += courses[i].getExercises().get(sub.getWeek());
                }
                System.out.println("");
                System.out.println("yhteensä: " + sumExer + "/" + maxExer + " tehtävää " + sumHrs + " tuntia\n");
                Integer[] s = statsMap.getOrDefault(courses[i].getName(), new Integer[3]);
                System.out.println("kurssilla yhteensä " + s[0] + " palautusta, palautettuja tehtäviä " + s[1] + " kpl, aikaa käytetty yhteensä " + s[2] + " tuntia\n");
            }
        }
    }
}
