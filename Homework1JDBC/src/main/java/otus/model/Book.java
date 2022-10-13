package otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private Long id;
    private final String name;
    private final Author author;
    private final Genre genre;

    public String toString() {
        return "Книга: id - " + this.getId()
                + ", Название - " + this.getName() + "\n"
                + this.getAuthor() + "\n"
                + this.getGenre()
                + "\n-------------";
    }
}
