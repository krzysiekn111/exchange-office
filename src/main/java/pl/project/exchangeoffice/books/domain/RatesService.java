package pl.project.exchangeoffice.books.domain;

import org.apache.commons.lang3.StringUtils;
import pl.project.exchangeoffice.books.MainView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RatesService {

//    private MainView mainView = new MainView();
    private static RatesService ratesService;
    private List<Rates> rates;

    public List<Rates> getRates() {
        return new ArrayList<>(rates);
    }

    private RatesService() throws IOException, InterruptedException {
        this.rates = getCurrencies();
    }

    public static RatesService getInstance() throws IOException, InterruptedException {
        if (ratesService == null) {
            ratesService = new RatesService();
        }
        return ratesService;
    }

    public List<Rates> getCurrencies() throws IOException, InterruptedException {
        String response = getConnection();
        int ids = StringUtils.countMatches(response, "id");
        List<Rates> rates = new ArrayList<>();

        for (int i = 0; i < ids; i++) {
            rates.add(getCurrencyValue(i+1));
        }

        return rates;
    }
    public String getConnection() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090/v1/office/rates"))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public Rates getCurrencyValue(int id) throws IOException, InterruptedException {
        String response = getConnection();
        System.out.println(response + "response");
        String[] s = response.split("\"currencyId\":" + id + ",\"exchangeRateToPLN\":");
        String[] sc = s[1].split(",");
        String[] scv = sc[1].split("\"currencyName\":\"");
        String scvb = scv[1].split("\"")[0];

        Rates rate = new Rates(
                scvb,
                Float.valueOf(sc[0])
        );
        return rate;
    }

}
