package me.igorkudashev.crud.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors
public class PatchGradeInfoRequest {
    @NotNull
    private Long id;

    private String grade;
}
