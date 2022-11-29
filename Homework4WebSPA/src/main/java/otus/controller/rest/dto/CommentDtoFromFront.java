package otus.controller.rest.dto;

import lombok.Data;

@Data
public class CommentDtoFromFront {

    private Long id;
    private String text;
    private Long book;
}
