package otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Genre {
    private Long id;
    private final String name;

    public String toString() {
        return "Жанр:  id - " + this.getId() +
                ", Вид - " + this.getName();
    }
}
