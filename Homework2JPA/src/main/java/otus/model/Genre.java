package otus.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    public Genre(String name) {

        this.name = name;
    }

    public String toString() {
        return "Жанр:  id - " + this.getId() +
                ", Вид - " + this.getName();
    }
}
