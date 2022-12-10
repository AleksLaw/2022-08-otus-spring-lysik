package otus.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otus.controller.rest.dto.CommentDto;
import otus.controller.rest.mapper.CommentMapper;
import otus.dao.CommentDao;
import otus.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    @PostMapping("api/comment")
    public Mono<CommentDto> saveComment(@RequestBody CommentDto commentDto) {
        return commentDao.save(commentMapper.toModel(commentDto))
                .map(commentMapper::toDto);
    }

    @GetMapping(value = {"api/comment"})
    public Flux<CommentDto> allComments() {
        return commentDao.findAll()
                .map(commentMapper::toDto);
    }

    @PutMapping("api/comment/{id}")
    public Mono<CommentDto> editComment(@PathVariable("id") String id, @RequestBody CommentDto commentDto) {
        Comment comment = commentMapper.toModel(commentDto);
        comment.setId(id);
        return commentDao.save(comment)
                .map(commentMapper::toDto);
    }

    @DeleteMapping("api/comment/{id}")
    public HttpStatus deleteComment(@PathVariable("id") String id) {
        commentDao.delete(Objects.requireNonNull(commentDao.findById(id).block())).block();
        return HttpStatus.OK;
    }
}
