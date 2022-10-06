package homework.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Student {
    private final String name;
    private final String surname;

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
