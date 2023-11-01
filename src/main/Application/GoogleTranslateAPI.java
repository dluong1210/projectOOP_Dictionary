package Application;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class GoogleTranslateAPI {
//    private static GoogleTranslateAPI instance;
//
//    public static GoogleTranslateAPI getInstance() {
//        if (instance == null) {
//            instance = new GoogleTranslateAPI();
//        }
//        return instance;
//    }

    private static String toRequest(String text, String langueIn, String langueOut) {
        return "q=" + text + "&target=" + langueOut + "&source=" + langueIn;
    }

    private static String getResponseJSON(String text, String langueIn, String langueOut)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "b04d3b81a1msh8d11367a8f6a54cp19e262jsn0d4267811b92")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(toRequest(text, langueIn, langueOut)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        return response.body();
    }

    public static String translate(String text, String langueIn, String langueOut)
            throws IOException, InterruptedException {
        JSONObject response = new JSONObject(getResponseJSON(text, langueIn, langueOut));

        return response.getJSONObject("data")
                       .getJSONArray("translations")
                       .getJSONObject(0)
                       .getString("translatedText");
    }

    public static void main(String[] argv) throws IOException, InterruptedException {
        String test = "Lucas goes to school every day of the week. He has many subjects to go to each school day: "
                + "English, art, science, mathematics, gym, and history. His mother packs a big backpack full of"
                + " books and lunch for Lucas.";
        System.out.println(translate(test, "en", "vi"));
    }
}

//{"data":
//        {"translations":
//            [{"translatedText":"hello world"}
//            ]
//        }
//}