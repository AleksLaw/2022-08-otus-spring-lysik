package otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.AuthorDao;
import otus.model.Author;
import otus.service.AuthorService;
import otus.service.IOService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    public static final String INPUT_ID_AUTHOR = "Введите id автора";
    public static final String INPUT_SURNAME = "Ведите фамилию";
    public static final String INPUT_NAME = "Ведите Ведите имя";
    public static final String AUTHOR_WITH_ID = "Автор с ид = ";
    public static final String DELETED = " удален";
    public static final String AUTHOR_COUNT = "Сейчас у нас количество авторов = ";
    public static final String NOT_FIND = "Нет такого! создаем!";
    public static final String SAVE_AUTHOR = "Сохранение автора\n";
    private final AuthorDao authorDAO;
    private final IOService ioService;

    @Override
    public long saveAuthor() {
        ioService.outputString(SAVE_AUTHOR);
        long id = authorDAO.insert(createAuthor());
        ioService.outputString(getAuthorById(id).toString());
        return id;
    }

    @Override
    public Author getAuthor() {
        getAllAuthor();
        ioService.outputString(INPUT_ID_AUTHOR);
        Author author = authorDAO.getById(ioService.getLong());
        ioService.outputString(author.toString());
        return author;
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDAO.getById(id);
    }

    @Override
    public List<Author> getAllAuthor() {
        List<Author> all = authorDAO.getAll();
        all.forEach(s -> ioService.outputString(s.toString()));
        return all;
    }

    @Override
    public Long updateAuthor() {
        Long id = checkId();
        Author author = createAuthor();
        author.setId(id);
        long rez = authorDAO.update(author);
        ioService.outputString(getAuthorById(id).toString());
        return rez;
    }

    @Override
    public long countAuthor() {
        long count = authorDAO.count();
        ioService.outputString(AUTHOR_COUNT + count);
        return count;
    }

    @Override
    public long deleteAuthorById(long id) {
        long rez = authorDAO.deleteById(id);
        ioService.outputString(AUTHOR_WITH_ID + id + DELETED);
        return rez;
    }

    @Override
    public long deleteAuthor() {
        return deleteAuthorById(checkId());
    }

    @Override
    public Author getOrSaveAuthor() {
        ioService.outputString(INPUT_ID_AUTHOR);
        if (getAllAuthor().isEmpty()) {
            return getAuthorById(saveAuthor());
        } else {
            try {
                return getAuthorById(ioService.getLong());
            } catch (Exception e) {
                ioService.outputString(NOT_FIND);
                return getAuthorById(saveAuthor());
            }
        }
    }

    private Author createAuthor() {
        ioService.outputString(INPUT_NAME);
        String name = ioService.getString();
        ioService.outputString(INPUT_SURNAME);
        String surname = ioService.getString();
        return new Author(name, surname);
    }

    private Long checkId() {
        List<Long> collect = getAllAuthor().stream().map(Author::getId).collect(Collectors.toList());
        Long id;
        do {
            ioService.outputString(INPUT_ID_AUTHOR);
            id = ioService.getLong();
        } while (!collect.contains(id));
        return id;
    }
}
