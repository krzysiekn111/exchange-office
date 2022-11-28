package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import pl.project.exchangeoffice.books.domain.Book;
import pl.project.exchangeoffice.books.domain.BookService;
import pl.project.exchangeoffice.books.domain.Rates;
import pl.project.exchangeoffice.books.domain.RatesService;

import java.io.IOException;

@Route
public class MainView extends VerticalLayout {

    private TextField filter = new TextField();
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);
    private Grid<Rates> grid2 = new Grid<>(Rates.class);
    private RatesService ratesService = RatesService.getInstance();


    public MainView() throws IOException, InterruptedException {
        grid.setColumns("title", "author", "publicationYear", "type");
        grid2.setColumns("currency", "exchangeRateToPLN");
        setSizeFull();
        refresh();
        refreshGrid2();
        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        add(filter, grid, grid2);
    }

    public void refresh() {
        grid.setItems(bookService.getBooks());
    }

    public void refreshGrid2() {
        grid2.setItems(ratesService.getRates());
    }

    private void update() {
        grid.setItems(bookService.findByTitle(filter.getValue()));
    }

}