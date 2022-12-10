package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.BookDto;
import otus.controller.rest.dto.CommentDto;
import otus.dao.BookDao;
import otus.model.Book;
import otus.model.Comment;

@Service
@Data
public class CommentMapper {
    private final BookDao bookDao;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final BookMapper bookMapper;

    public Comment toModel(CommentDto commentDto) {
        String id = commentDto.getBook().getId();
        Book book = bookDao.findById(id).block();
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
