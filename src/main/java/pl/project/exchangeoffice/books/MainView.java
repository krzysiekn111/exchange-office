package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.project.exchangeoffice.books.domain.Book;
import pl.project.exchangeoffice.books.domain.BookService;
import pl.project.exchangeoffice.books.domain.Rates;
import pl.project.exchangeoffice.books.domain.Service;

import java.io.IOException;

@Route
public class MainView extends VerticalLayout {

    private TextField filter = new TextField();
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);
//    private Grid<Rates> grid2 = new Grid<>(Rates.class);

    @Autowired
    private Service service;


    public MainView() throws IOException, InterruptedException {
        Button button = new Button("Waluty");
        button.addClickListener(e -> {
            try {
                Rates rates = service.getCurrencyValue("usd", 2);
                System.out.println(rates);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        grid.setColumns("title", "author", "publicationYear", "type");
//        grid2.setColumns("currency", "exchangeRateToPLN");
        setSizeFull();
        refresh();
//        refreshGrid2();
        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        add(filter, grid, button);
//        add(grid2);
    }

    public void refresh() {
        grid.setItems(bookService.getBooks());
    }
//    public void refreshGrid2() throws IOException, InterruptedException {
//        grid2.setItems(service.getCurrencyValue("usd", 2));
//    }

    private void update() {
        grid.setItems(bookService.findByTitle(filter.getValue()));
    }
}