package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CategoryRequest {
    private String name;
    private String description;
    private UUID parentId;
}
