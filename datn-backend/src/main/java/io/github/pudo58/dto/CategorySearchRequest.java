package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategorySearchRequest extends PageParam {
    private String keyword;
}
