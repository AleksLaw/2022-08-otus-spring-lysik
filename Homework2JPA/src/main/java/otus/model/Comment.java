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
@NamedEntityGraph(name = "commentGraph",
        attributeNodes = {@NamedAttributeNode(value = "book", subgraph = "bookGraph")},
        subgraphs = {
                @NamedSubgraph(name = "bookGraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "id"),
                                @NamedAttributeNode(value = "name")
                        })
        })
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment_text")
    private String text;
    @JoinColumn(name = "book_id")
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }

    public String toString() {
        return "Комментарий: id - " + this.getId()
                + " " + this.getText()
                + ", К книге - " + this.book.getName()
                + " с id - " + this.book.getId()
                ;
    }
}
