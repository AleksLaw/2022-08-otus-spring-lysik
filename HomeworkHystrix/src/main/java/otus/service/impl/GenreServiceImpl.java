package otus.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.GenreDao;
import otus.model.Genre;
import otus.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDAO;

    @HystrixCommand(commandKey = "keyGenre")
    @Override
    public Genre saveGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    @HystrixCommand(commandKey = "keyGenre")
    @Override
    public Genre getGenre(long id) {
        return genreDAO.findById(id).orElse(null);
    }

    @HystrixCommand(commandKey = "keyGenre")
    @Override
    public List<Genre> getAllGenre() {
        return genreDAO.findAll();
    }

    @HystrixCommand(commandKey = "keyGenre")
    @Override
    public Genre updateGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    @HystrixCommand(commandKey = "keyGenre")
    @Override
    public void deleteGenre(Genre genre) {
        genreDAO.deleteById(genre.getId());
    }
}
