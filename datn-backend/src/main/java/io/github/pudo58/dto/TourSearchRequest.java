package io.github.pudo58.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class TourSearchRequest extends PageParam {
    private String keyword;
    private UUID categoryId;
}
