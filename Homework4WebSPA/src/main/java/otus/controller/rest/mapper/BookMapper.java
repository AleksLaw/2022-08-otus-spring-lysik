package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.BookDtoFromFront;
import otus.controller.rest.dto.GenreDto;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;
import otus.service.AuthorService;
import otus.service.GenreService;

@Service
@Data
public class BookMapper {
    private final AuthorService authorService;
    private final GenreService genreService;

    public Book toModel(BookDtoFromFront bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                authorService.getAuthor(bookDto.getAuthor()),
                genreService.getGenre(bookDto.getGenre()),
                null
        );
    }

    public BookDto toDto(Book book) {
        Author rAuthor = book.getAuthor();
        Genre rGenre = book.getGenre();
        return new BookDto(
                book.getId(),
                book.getName(),
                new AuthorDto(rAuthor.getId(), rAuthor.getName(), rAuthor.getSurname()),
                new GenreDto(rGenre.getId(), rGenre.getName())
        );
    }
}
