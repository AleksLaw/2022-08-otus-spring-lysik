package otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otus.model.Book;
import otus.model.Comment;
import otus.service.BookService;
import otus.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("comment/save")
    public String savePage(Model model) {
        List<Book> allBook = bookService.getAllBook();
        model.addAttribute("books", allBook);
        return "comment/commentSave";
    }

    @PostMapping("comment/save")
    public String saveComment(Comment comment) {
        commentService.saveComment(comment);
        return "redirect:/comment/all";
    }

    @GetMapping("comment/all")
    public String AllCommentsPage(Model model) {
        List<Comment> allComment = commentService.getAllComment();
        model.addAttribute("comments", allComment);
        return "comment/commentList";
    }

    @GetMapping("comment/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Comment comment = commentService.getComment(id);
        model.addAttribute("comment", comment);
        return "comment/commentEdit";
    }

    @PostMapping("comment/edit")
    public String editComment(Comment comment) {
        Comment fromForm = commentService.getComment(comment.getId());
        Book book = bookService.getBook(fromForm.getBook().getId());
        fromForm.setBook(book);
        fromForm.setText(comment.getText());
        commentService.saveComment(fromForm);
        return "redirect:/comment/all";
    }

    @GetMapping("comment/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        Comment comment = commentService.getComment(id);
        model.addAttribute("comment", comment);
        return "comment/commentDelete";

    }

    @PostMapping("comment/delete")
    public String deleteComment(Comment comment) {
        commentService.deleteComment(comment);
        return "redirect:/comment/all";
    }

}
