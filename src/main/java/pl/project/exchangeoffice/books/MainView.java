package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import pl.project.exchangeoffice.books.domain.Rates;
import pl.project.exchangeoffice.books.domain.RatesService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Route
public class MainView extends VerticalLayout {

    private Text text = new Text(isTheDayAHoliday().equals(true) ?
            "DZISIAJ OBRÓT WALUTAMI JEST NIEDOSTĘPNY" : "DZISIAJ DZIEŃ ROBOCZY" );
    private Grid<Rates> grid = new Grid<>(Rates.class);
    private RatesService ratesService = RatesService.getInstance();

    public MainView() throws IOException, InterruptedException {
        grid.setColumns("currency", "exchangeRateToPLN");
        add(grid, text);
        setSizeFull();
        refreshGrid();
    }

    public void refreshGrid() {
        grid.setItems(ratesService.getRates());
    }


    private Boolean isTheDayAHoliday() throws IOException, InterruptedException {
        int year = LocalDate.now().getYear();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://date.nager.at/api/v3/PublicHolidays/" + year + "/PL"))
                .build();
        String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        String today = LocalDate.now().toString();
        return response.contains(today);

    }
}