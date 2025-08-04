package org.api.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Producer {

    private Long id;
    private String name;

    @Getter
    private static List<Producer> producers = new ArrayList<>();
    static {
        Producer Madhouse = new Producer(1L, "Madhouse");
        Producer Pierrot = new Producer(2L, "Pierrot");
        Producer WitStudio = new Producer(3L, "Wit Studio");
        Producer Bones = new Producer(4L, "Bones");
        Producer ToeiAnimation = new Producer(5L, "ToeiAnimation");
        Collections.addAll(producers, Madhouse, Pierrot, WitStudio, Bones, ToeiAnimation);
    }

    public Producer() {}

    public Producer(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
