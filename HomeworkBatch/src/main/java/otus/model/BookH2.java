package otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "books")
public class BookH2 {

    @Id
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "genre_id")
    private Long genreId;
}
