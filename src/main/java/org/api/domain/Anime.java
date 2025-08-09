package org.api.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Anime {

    private Long id;
    private String name;

    public Anime() {}

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return Objects.equals(id, anime.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
