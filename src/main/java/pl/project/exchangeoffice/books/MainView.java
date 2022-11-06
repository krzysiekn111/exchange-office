package pl.project.exchangeoffice.books;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.project.exchangeoffice.books.domain.Book;
import pl.project.exchangeoffice.books.domain.BookService;

@Route
public class MainView extends VerticalLayout {

    public void refresh() {
        grid.setItems(bookService.getBooks());
    }

    public MainView() {
        grid.setColumns("title", "author", "publicationYear", "type");
        add(grid);
        setSizeFull();
        refresh();
    }
    private BookService bookService = BookService.getInstance();
    private Grid<Book> grid = new Grid<>(Book.class);


}