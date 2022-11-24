package otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.AuthorDao;
import otus.model.Author;
import otus.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDAO;

    @Override
    public Author saveAuthor(Author author) {
        return authorDAO.save(author);
    }

    @Override
    public Author getAuthor(long id) {
        return authorDAO.findById(id).orElse(null);
    }

    @Override
    public List<Author> getAllAuthor() {
        return authorDAO.findAll();
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorDAO.save(author);
    }

    @Override
    public void deleteAuthor(Author author) {
        authorDAO.deleteById(author.getId());
    }
}
