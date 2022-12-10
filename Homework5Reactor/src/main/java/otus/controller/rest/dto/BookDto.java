package otus.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private String id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;

}
