package org.api.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Producer {

    private Long id;

    private String name;
    private LocalDateTime createdAt;

    @Getter
    private static List<Producer> producers = new ArrayList<>();
    static {
        Producer Madhouse = new Producer(1L, "Madhouse", LocalDateTime.now());
        Producer Pierrot = new Producer(2L, "Pierrot", LocalDateTime.now());
        Producer WitStudio = new Producer(3L, "Wit Studio", LocalDateTime.now());
        Producer Bones = new Producer(4L, "Bones", LocalDateTime.now());
        Producer ToeiAnimation = new Producer(5L, "ToeiAnimation", LocalDateTime.now());
        Collections.addAll(producers, Madhouse, Pierrot, WitStudio, Bones, ToeiAnimation);
    }

    public Producer() {}

    public Producer(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }
}
