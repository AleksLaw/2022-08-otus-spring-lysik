package otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Author {

    private Long id;
    private final String name;
    private final String surname;

    public String toString() {
        return "Автор: id - " + this.getId()
                + ", Имя - " + this.getName()
                + ", Фамилия - " + this.getSurname();
    }
}

