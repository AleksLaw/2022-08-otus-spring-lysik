package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.AuthorDto;
import otus.model.Author;

@Service
@Data
public class AuthorMapper {

    public Author toModel(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getName(),
                authorDto.getSurname()
        );
    }

    public AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getSurname()
        );
    }
}
