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

//    private static String getResponseJSON(String text, String langueIn, String langueOut)
//            throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
//                .header("content-type", "application/x-www-form-urlencoded")
//                .header("Accept-Encoding", "application/gzip")
//                .header("X-RapidAPI-Key", "a1eee83ba1msh94476e54672bd76p13321cjsn31948ee29ba1")
//                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
//                .method("POST", HttpRequest.BodyPublishers.ofString(toRequest(text, langueIn, langueOut)))
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
//        return response.body();
//    }

    private static String getResponseJSON(String text, String langueIn, String langueOut)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "b04d3b81a1msh8d11367a8f6a54cp19e262jsn0d4267811b92")
                .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("from=" + langueIn + "&to=" + langueOut + "&text=" + text))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        return response.body();
    }

    public static String translate(String text, String langueIn, String langueOut)
            throws IOException, InterruptedException {
        if (langueIn.equals(langueOut)) return text;
        JSONObject response = new JSONObject(getResponseJSON(text, langueIn, langueOut));

        return response.getString("trans");

//        return response.getJSONObject("data")
//                       .getJSONArray("translations")
//                       .getJSONObject(0)
//                       .getString("translatedText");
    }

    public static void main(String[] argv) throws IOException, InterruptedException {
        String test = "First, I wake up. Then, I get dressed. I walk to school. I do not ride a bike. I do not ride the bus. I like to go to school. It rains. I do not like rain. I eat lunch. I eat a sandwich and an apple.\n" +
                "\n" +
                "I play outside. I like to play. I read a book. I like to read books. I walk home. I do not like walking home. My mother cooks soup for dinner. The soup is hot. Then, I go to bed. I do not like to go to bed";
//        getResponseJSON2(test, "en", "vi");
        System.out.println(translate(test, "en", "vi"));
    }
}
