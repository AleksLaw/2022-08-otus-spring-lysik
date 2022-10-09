package otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.GenreDao;
import otus.model.Genre;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@Service
@RequiredArgsConstructor
public class GenreService {


    private final GenreDao genreDAO;
    private final Scanner sc = new Scanner(in);

    public long saveGenre() {
        out.println("Создание жанра\nВедите название");
        Genre genre = new Genre(sc.nextLine());
        long id = genreDAO.insert(genre);
        out.println("Запись с id-" + id + " добавлена");
        return id;
    }

    public Genre getGenreById(long id) {
        return genreDAO.getById(id);
    }

    public List<Genre> getAllGenre() {
        return genreDAO.getAll();
    }

    public Genre getOrSaveGenre() {
        out.println("Введите id жанра");
        List<Genre> allGenre = getAllGenre();
        if (allGenre.isEmpty()) {
            return getGenreById(saveGenre());
        } else {
            allGenre.forEach(out::println);
            long id_genre = sc.nextLong();
            try {
                return getGenreById(id_genre);
            } catch (Exception e) {
                out.println("Нет такого! создаем!");
                return getGenreById(saveGenre());
            }
        }
    }
}
