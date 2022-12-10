package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.AuthorDto;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.BookDtoFromFront;
import otus.controller.rest.dto.GenreDto;
import otus.dao.AuthorDao;
import otus.dao.GenreDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;


@Service
@Data
public class BookMapper {
    private final AuthorDao authorService;
    private final GenreDao genreDao;

    public Book toModel(BookDtoFromFront bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getName(),
                authorService.findById(bookDto.getAuthor()).block(),
                genreDao.findById(bookDto.getGenre()).block(),
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
