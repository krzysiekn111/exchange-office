package pl.project.exchangeoffice.books.domain;


import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class Service {
    public Float getConnection(String currencyURL) throws Exception {
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
        return exchangeRate;
    }

    @Scheduled(fixedRate = 5000)
    private void use() throws IOException, InterruptedException {
        getExchangeRates(1);
    }

    public void getExchangeRates( int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/v1/office/rates"))
                .build();


        int augmentedId = id + 1;

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        String  jsonObject1 = new JSONObject(response.body()).toString();

        String[] s = response.body().split("\"currencyId\":" + id + ",\"exchangeRateToPLN\":");
        String[] sc = s[1].split("}");
//        Float exchangeRate = Float.valueOf(sc[1]);

        System.out.println(response.body());
        System.out.println(sc[0]);
    }
}
