package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.AuthorDao;
import otus.model.Author;
import otus.service.domenservice.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDAO;

    @Override
    @Transactional
    public Author saveAuthor(String name, String surname) {
        return authorDAO.save(new Author(name, surname));
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
    @Transactional
    public Author updateAuthor(Author author) {
        return authorDAO.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Author author) {
        authorDAO.deleteById(author.getId());
    }
}