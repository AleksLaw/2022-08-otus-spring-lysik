package otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import otus.model.Genre;
import otus.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("genre/save")
    public String savePage() {
        return "genre/genreSave";
    }

    @PostMapping("genre/save")
    public String saveGenre(Genre genre) {
        genreService.saveGenre(genre);
        return "redirect:/genre/all";
    }

    @GetMapping("genre/all")
    public String AllGenresPage(Model model) {
        List<Genre> allGenre = genreService.getAllGenre();
        model.addAttribute("genres", allGenre);
        return "genre/genreList";
    }

    @GetMapping("genre/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Genre genre = genreService.getGenre(id);
        model.addAttribute("genre", genre);
        return "genre/genreEdit";
    }

    @PostMapping("genre/edit")
    public String editGenre(Genre genre) {
        Genre fromForm = genreService.getGenre(genre.getId());
        fromForm.setName(genre.getName());
        genreService.saveGenre(fromForm);
        return "redirect:/genre/all";
    }

    @GetMapping("genre/delete")
    public String deletePage(@RequestParam("id") long id, Model model) {
        Genre genre = genreService.getGenre(id);
        model.addAttribute("genre", genre);
        return "genre/genreDelete";
    }

    @PostMapping("genre/delete")
    public String deleteGenre(Genre genre) {
        try {
            genreService.deleteGenre(genre);
        } catch (Exception e) {
            return "/genre/genreError";
        }
        return "redirect:/genre/all";
    }
}
