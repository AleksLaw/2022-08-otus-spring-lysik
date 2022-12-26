package otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("book/save")
    public String savePage(Model model) {
        List<Author> allAuthor = authorService.getAllAuthor();
        List<Genre> allGenre = genreService.getAllGenre();
        model.addAttribute("authors", allAuthor);
        model.addAttribute("genres", allGenre);
        return "book/bookSave";
    }

    @PostMapping("book/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/book/all";
    }

    @GetMapping(value = {"book/all"})
    public String AllBooksPage(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "book/bookList";
    }

    @GetMapping("book/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getBook(id);
        List<Author> allAuthor = authorService.getAllAuthor();
        List<Genre> allGenre = genreService.getAllGenre();
        model.addAttribute("authors", allAuthor);
        model.addAttribute("genres", allGenre);
        model.addAttribute("book", book);
        return "book/bookEdit";
    }

    @PostMapping("book/edit")
    public String editBook(Book book) {
        Book fromForm = bookService.getBook(book.getId());
        fromForm.setGenre(book.getGenre());
        fromForm.setAuthor(book.getAuthor());
        fromForm.setName(book.getName());
        bookService.saveBook(fromForm);
        return "redirect:/book/all";
    }

    @GetMapping("book/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "book/bookDelete";

    }

    @PostMapping("book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        Book book = bookService.getBook(id);
        bookService.deleteBook(book);
        return "redirect:/book/all";
    }

    @GetMapping("book/comments")
    public String commentPage(@RequestParam("id") long id, Model model) {
        Book book = bookService.getBook(id);
        List<Comment> comments = book.getComments();
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "book/bookComment";
    }
}
