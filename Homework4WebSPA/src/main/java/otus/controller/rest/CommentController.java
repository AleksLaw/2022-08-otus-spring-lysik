package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.mapper.CommentMapper;
import otus.model.Comment;
import otus.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("api/comment")
    public CommentDto saveComment(@RequestBody CommentDto commentDto) {
        return commentMapper.toDto(
                commentService.saveComment(
                        commentMapper.toModel(commentDto)));
    }

    @GetMapping(value = {"api/comment"})
    public List<CommentDto> allComments() {
        return commentService.getAllComment()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("api/comment/{id}")
    public CommentDto editComment(@PathVariable("id") long id, @RequestBody CommentDto commentDto) {
        Comment comment = commentMapper.toModel(commentDto);
        comment.setId(id);
        return commentMapper.toDto(commentService.saveComment(comment));
    }

    @DeleteMapping("api/comment/{id}")
    public HttpStatus deleteComment(@PathVariable("id") long id) {
        commentService.deleteComment(commentService.getComment(id));
        return HttpStatus.OK;
    }
}
