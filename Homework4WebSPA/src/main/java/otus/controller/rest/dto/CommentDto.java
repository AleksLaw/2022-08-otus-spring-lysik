package otus.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String text;
    private BookDto book;

}
