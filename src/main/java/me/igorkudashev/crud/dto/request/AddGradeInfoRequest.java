package me.igorkudashev.crud.dto.request;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@Accessors
public class AddGradeInfoRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long lessonId;

    private String grade;
    @NotNull
    private LocalDate date;
}
