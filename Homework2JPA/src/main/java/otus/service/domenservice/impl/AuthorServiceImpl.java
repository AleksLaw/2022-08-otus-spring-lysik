package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.AuthorDao;
import otus.model.Author;
import otus.service.domenservice.AuthorService;
import otus.service.ioservice.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    public static final String INPUT_ID_AUTHOR = "Введите id автора";
    public static final String INPUT_SURNAME = "Введите фамилию";
    public static final String INPUT_NAME = "Введите имя";
    public static final String AUTHOR_WITH_ID = "Автор с ид = ";
    public static final String DELETED = " удален";
    public static final String SAVE_AUTHOR = "Сохранение автора";
    public static final String NOT_DELETED = "Автор не может быть удален. Он является автором книги которая у нас есть";
    public static final String NOT_FIND = "Не найден";
    private final AuthorDao authorDAO;
    private final IOService ioService;

    @Override
    @Transactional
    public Author saveAuthor() {
        ioService.outputString(SAVE_AUTHOR);
        Author author = authorDAO.save(createAuthor());
        ioService.outputString(author.toString());
        return author;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthor() {
        Author author = authorDAO.getById(getId()).orElse(null);
        if (author == null) {
            ioService.outputString(NOT_FIND);
            getAllAuthor();
        } else {
            ioService.outputString(author.toString());
        }
        return author;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthor() {
        List<Author> allAuthors = authorDAO.getAll();
        allAuthors.forEach(author -> ioService.outputString(author.toString()));
        return allAuthors;
    }

    @Override
    @Transactional
    public Author updateAuthor() {
        Long id = checkAuthor().getId();
        Author author = createAuthor();
        author.setId(id);
        Author update = authorDAO.update(author);
        ioService.outputString(update.toString());
        return update;
    }

    @Override
    @Transactional
    public long deleteAuthor() {
        Author checkAuthor = checkAuthor();
        long rez = 0;
        try {
            rez = authorDAO.deleteById(checkAuthor.getId());
            ioService.outputString(AUTHOR_WITH_ID + checkAuthor.getId() + DELETED);
        } catch (RuntimeException e) {
            ioService.outputString(NOT_DELETED);
        }
        return rez;
    }

    private Author createAuthor() {
        ioService.outputString(INPUT_NAME);
        String name = ioService.getString();
        ioService.outputString(INPUT_SURNAME);
        String surname = ioService.getString();
        return new Author(name, surname);
    }

    private long getId() {
        ioService.outputString(INPUT_ID_AUTHOR);
        return ioService.getLong();
    }

    private Author checkAuthor() {
        Author author;
        do {
            author = authorDAO.getById(getId()).orElse(null);
            if (author == null) {
                ioService.outputString(NOT_FIND);
                getAllAuthor();
            }
        } while (author == null);
        return author;
    }
}
