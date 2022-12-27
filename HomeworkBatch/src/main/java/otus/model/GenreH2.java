package otus.model;

/**
 * @author Aleksander Lysik
 * @since 26.12.2022
 */

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
@Table(name = "genres")
public class GenreH2 {

    @Id
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
}