package otus.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "books")
@NamedEntityGraph(name = "book-graph",
        attributeNodes = {@NamedAttributeNode("author"),
                @NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "author_id")
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Author author;

    @JoinColumn(name = "genre_id")
    @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public String toString() {
        return "Книга: id - " + this.getId()
                + ", Название - " + this.getName() + "\n"
                + this.getAuthor() + "\n"
                + this.getGenre()
                + "\n-------------";
    }
}
