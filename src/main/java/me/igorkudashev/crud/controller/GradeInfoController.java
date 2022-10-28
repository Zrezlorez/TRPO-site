package me.igorkudashev.crud.controller;

import lombok.RequiredArgsConstructor;
import me.igorkudashev.crud.dto.request.AddGradeInfoRequest;
import me.igorkudashev.crud.exception.LessonNotFoundException;
import me.igorkudashev.crud.exception.UserNotFoundException;
import me.igorkudashev.crud.model.GradeInfo;
import me.igorkudashev.crud.model.Lesson;
import me.igorkudashev.crud.model.User;
import me.igorkudashev.crud.repository.GradeInfoRepository;
import me.igorkudashev.crud.repository.LessonRepository;
import me.igorkudashev.crud.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/gradeinfo")
@RequiredArgsConstructor
public class GradeInfoController {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final GradeInfoRepository gradeInfoRepository;

    @PostMapping("/")
    public Long setGrade(@Valid @RequestBody AddGradeInfoRequest request) {

        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Lesson lesson = lessonRepository
                .findById(request.getUserId())
                .orElseThrow(LessonNotFoundException::new);

        return gradeInfoRepository.save(new GradeInfo()
                .setUser(user)
                .setLesson(lesson)
                .setGrade(request.getGrade())
                .setDate(request.getDate())).getId();
    }
}
