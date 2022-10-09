package otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.BookDao;
import otus.model.Book;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDAO;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final Scanner sc = new Scanner(in);

    public long saveBook() {
        out.println("Создание книги\nВедите название книги");
        long id = bookDAO.insert(
                new Book(sc.nextLine(), authorService.getOrSaveAuthor(), genreService.getOrSaveGenre()));
        out.println("Запись с id-" + id + " добавлена");
        return id;
    }

    public Book getBookById(long id) {
        return bookDAO.getById(id);
    }

}
