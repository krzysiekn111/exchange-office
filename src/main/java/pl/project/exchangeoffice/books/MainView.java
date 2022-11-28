package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.project.exchangeoffice.books.domain.Rates;
import pl.project.exchangeoffice.books.domain.RatesService;

import java.io.IOException;

@Route
public class MainView extends VerticalLayout {

    private Grid<Rates> grid2 = new Grid<>(Rates.class);
    private RatesService ratesService = RatesService.getInstance();


    public MainView() throws IOException, InterruptedException {
        grid2.setColumns("currency", "exchangeRateToPLN");
        add(grid2);
        setSizeFull();
        refreshGrid();
    }

    public void refreshGrid() {
        grid2.setItems(ratesService.getRates());
    }

}