package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.service.domenservice.CommentService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandComment {
    private final CommentService commentService;

    @ShellMethod(value = "Сохранение коментария", key = {"sc", "SaveComment"})
    public void saveComment() {
        commentService.saveComment();
    }

    @ShellMethod(value = "Получение всех коментариев для книги", key = {"ac", "AllCommentBook"})
    public void getAllCommentsByBook() {
        commentService.getAllCommentByBookId();
    }

    @ShellMethod(value = "Получение коментария", key = {"gc", "GetComment"})
    public void getComment() {
        commentService.getCommentById();
    }

    @ShellMethod(value = "Обновление коментария", key = {"uc", "UpdateComment"})
    public void updateComment() {
        commentService.updateComment();
    }

    @ShellMethod(value = "Удаление коментария", key = {"dc", "DeleteComment"})
    public void deleteComment() {
        commentService.deleteComment();
    }
}
