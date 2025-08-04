package org.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class ProducerGetResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
