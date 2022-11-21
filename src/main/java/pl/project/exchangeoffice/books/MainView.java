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
import pl.project.exchangeoffice.books.domain.Service;

@Route
public class MainView extends VerticalLayout {

    private TextField filter = new TextField();
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);

    @Autowired
    private Service service;


    public MainView() throws Exception {
        Button button = new Button("Waluty");
        button.addClickListener(e -> {
            try {
                service.getConnection("usd");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        grid.setColumns("title", "author", "publicationYear", "type");
        add(grid);
        setSizeFull();
        refresh();
        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> update());
        add(filter, grid, button);
    }

    public void refresh() {
        grid.setItems(bookService.getBooks());
    }

    private void update() {
        grid.setItems(bookService.findByTitle(filter.getValue()));
    }


}