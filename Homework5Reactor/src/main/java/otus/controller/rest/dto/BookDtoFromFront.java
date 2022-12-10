package otus.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDtoFromFront {

    private String id;
    private String name;
    private String author;
    private String genre;
}
