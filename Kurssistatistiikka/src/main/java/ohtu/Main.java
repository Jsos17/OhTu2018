package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
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

        System.out.println("opiskelijanumero: " + studentNr);
        System.out.println("");

        int sumExercises = 0;
        int sumHours = 0;
        for (Submission submission : subs) {
            sumExercises += submission.getExercises().size();
            sumHours += submission.getHours();
            System.out.println(submission);
        }

        System.out.println("");
        System.out.println("yhteensä: " + sumExercises + " tehtävää " + sumHours + " tuntia");
    }
}
