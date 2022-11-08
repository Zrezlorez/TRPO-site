package org.algiri.crud.dto.request;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
@Data
@Accessors
public class AddGradeInfoRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long lessonId;

    private String grade;
    @NotNull
    private Long dateId;
}
