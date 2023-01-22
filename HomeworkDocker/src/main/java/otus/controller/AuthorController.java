package otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otus.model.Author;
import otus.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("author/save")
    public String savePage() {
        return "author/authorSave";
    }

    @PostMapping("author/save")
    public String saveAuthor(Author author) {
        authorService.saveAuthor(author);
        return "redirect:/author/all";
    }

    @GetMapping("author/all")
    public String AllAuthorsPage(Model model) {
        List<Author> allAuthor = authorService.getAllAuthor();
        model.addAttribute("authors", allAuthor);
        return "author/authorList";
    }

    @GetMapping("author/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "author/authorEdit";
    }

    @PostMapping("author/edit")
    public String editAuthor(Author author) {
        Author fromForm = authorService.getAuthor(author.getId());
        fromForm.setSurname(author.getSurname());
        fromForm.setName(author.getName());
        authorService.saveAuthor(fromForm);
        return "redirect:/author/all";
    }

    @GetMapping("author/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        Author author = authorService.getAuthor(id);
        model.addAttribute("author", author);
        return "author/authorDelete";

    }

    @PostMapping("author/delete")
    public String deleteAuthor(Author author) {
        try {
            authorService.deleteAuthor(author);
        } catch (Exception e) {
            return "/author/authorError";
        }
        return "redirect:/author/all";
    }

}
