package otus.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDtoFromFront {

    private Long id;
    private String name;
    private Long author;
    private Long genre;
}
