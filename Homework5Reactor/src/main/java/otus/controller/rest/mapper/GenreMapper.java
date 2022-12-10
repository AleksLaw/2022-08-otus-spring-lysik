package otus.controller.rest.mapper;


import lombok.Data;
import org.springframework.stereotype.Service;
import otus.controller.rest.dto.GenreDto;
import otus.model.Genre;

@Service
@Data
public class GenreMapper {

    public Genre toModel(GenreDto authorDto) {
        return new Genre(
                authorDto.getId(),
                authorDto.getName()
        );
    }

    public GenreDto toDto(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName()
        );
    }
}
