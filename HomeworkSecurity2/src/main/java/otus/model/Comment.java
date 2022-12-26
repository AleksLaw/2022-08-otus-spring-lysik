package otus.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "comments")
@NamedEntityGraph(name = "comment-graph",
        attributeNodes = {@NamedAttributeNode("book")})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text")
    private String text;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public String toString() {
        return "Комментарий: id - " + this.getId()
                + " " + this.getText();
    }
}
