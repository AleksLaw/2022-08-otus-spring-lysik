package otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.AuthorDao;
import otus.model.Author;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao authorDAO;
    private final Scanner sc = new Scanner(in);

    public long saveAuthor() {
        out.println("Создание автора\nВедите имя");
        String name = sc.nextLine();
        out.println("Ведите фамилию");
        long id = authorDAO.insert(new Author(name, sc.nextLine()));
        out.println("Запись с id-" + id + " добавлена");
        return id;
    }

    public Author getAuthorById(long id) {
        return authorDAO.getById(id);
    }

    public List<Author> getAllAuthor() {
        return authorDAO.getAll();
    }

    public Author getOrSaveAuthor() {
        out.println("Введите id автора");
        List<Author> allAuthor = getAllAuthor();
        if (allAuthor.isEmpty()) {
            return getAuthorById(saveAuthor());
        } else {
            allAuthor.forEach(out::println);
            long id_author = sc.nextLong();
            try {
                return getAuthorById(id_author);
            } catch (Exception e) {
                out.println("Нет такого! создаем!");
                return getAuthorById(saveAuthor());
            }
        }
    }
}
