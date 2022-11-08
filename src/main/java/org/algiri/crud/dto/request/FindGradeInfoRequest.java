package org.algiri.crud.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


@Data
@Accessors
public class FindGradeInfoRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long dayId;
}
