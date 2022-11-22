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

    @Scheduled(fixedRate = 5000)
    private void use() throws IOException, InterruptedException {
        getCurrencyValue("chf", 4);
    }

    public String getConnection() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/v1/office/rates"))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public void getCurrencyValue(String currency, int id) throws IOException, InterruptedException {
        String response = getConnection();
        String[] s = response.split("\"currencyId\":" + id + ",\"exchangeRateToPLN\":");
        String[] sc = s[1].split(",");
        System.out.println(sc[0]);
        System.out.println(response);
        String[] scv = sc[1].split(",\"currencyName\":");
        String scvb = scv[0].split("}")[0];

//        Rates rate = getRate();
        Rates rates = new Rates(
                scvb,
                Float.valueOf(sc[0])
        );
        System.out.println(rates);
    }

//    private Rates getRate() {
//        return
//    }


}
