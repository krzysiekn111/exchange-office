package pl.project.exchangeoffice.books.domain;


import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Service {
    public void getConnection(String currencyURL) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.nbp.pl/api/exchangerates/rates/a/" + currencyURL+ "/"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonObject1 = new JSONObject(response.body()).toString();
        String[] s = jsonObject1.split(",\"effectiveDate");
        String[] sc = s[0].split("\"mid\":");
        Float exchangeRate = Float.valueOf(sc[1]);
        System.out.println(exchangeRate);



    }
}
