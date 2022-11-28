package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import pl.project.exchangeoffice.books.domain.Book;
import pl.project.exchangeoffice.books.domain.BookService;
import pl.project.exchangeoffice.books.domain.Rates;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

    private TextField filter = new TextField();
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);
    private Grid<Rates> grid2 = new Grid<>(Rates.class);


    public MainView() throws IOException, InterruptedException {
        Button button = new Button(getCurrencyValue(2).toString());
        grid.setColumns("title", "author", "publicationYear", "type");
        grid2.setColumns("currency", "exchangeRateToPLN");
        setSizeFull();
        refresh();
        refreshGrid2();
        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        add(filter, grid, button, grid2);
    }

    public void refresh() {
        grid.setItems(bookService.getBooks());
    }
    public void refreshGrid2() throws IOException, InterruptedException {
        grid2.setItems(getCurrencies());
    }

    private void update() {
        grid.setItems(bookService.findByTitle(filter.getValue()));
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
    public List<Rates> getCurrencies() throws IOException, InterruptedException {
        String response = getConnection();
        int ids = StringUtils.countMatches(response, "id");
        List<Rates> rates = new ArrayList<>();

        for (int i = 0; i < ids; i++) {
            rates.add(getCurrencyValue(i+1));
        }

        return rates;
    }
}