package otus.service.impl;

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

    @Override
    public Genre saveGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    @Override
    public Genre getGenre(long id) {
        return genreDAO.findById(id).orElse(null);
    }

    @Override
    public List<Genre> getAllGenre() {
        return genreDAO.findAll();
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreDAO.save(genre);
    }

    @Override
    public void deleteGenre(Genre genre) {
        genreDAO.deleteById(genre.getId());
    }
}
