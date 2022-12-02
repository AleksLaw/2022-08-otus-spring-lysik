package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.CommentDto;
import otus.model.Book;
import otus.model.Comment;
import otus.service.BookService;

@Service
@Data
public class CommentMapper {
    private final BookService bookService;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final BookMapper bookMapper;

    public Comment toModel(CommentDto commentDto) {
        Book book = bookService.getBook(commentDto.getBook().getId());
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                book
        );
    }

    public CommentDto toDto(Comment comment) {
        Book rBook = comment.getBook();
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                new BookDto(comment.getId(),
                        rBook.getName(),
                        authorMapper.toDto(rBook.getAuthor()),
                        genreMapper.toDto(rBook.getGenre())
                )
        );
    }
}
