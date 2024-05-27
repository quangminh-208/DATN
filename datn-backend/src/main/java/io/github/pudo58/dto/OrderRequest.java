package io.github.pudo58.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class OrderRequest extends PageParam{
    private UUID tourId;
    @JsonIgnore
    private UUID userId;
    private Date startDate;
    private Date endDate;
}
