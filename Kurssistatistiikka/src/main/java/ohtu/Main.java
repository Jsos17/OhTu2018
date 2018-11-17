package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.client.fluent.Request;

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
            }
        }
    }
}
