package otus.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(commandKey = "keyAuthor")
    @Override
    public Author getAuthor(long id) {
        return authorDAO.findById(id).orElse(null);
    }

    @HystrixCommand(commandKey = "keyAuthor")
    @Override
    public List<Author> getAllAuthor() {
        return authorDAO.findAll();
    }

    @HystrixCommand(commandKey = "keyAuthor")
    @Override
    public Author updateAuthor(Author author) {
        return authorDAO.save(author);
    }

    @HystrixCommand(commandKey = "keyAuthor")
    @Override
    public void deleteAuthor(Author author) {
        authorDAO.deleteById(author.getId());
    }
}
