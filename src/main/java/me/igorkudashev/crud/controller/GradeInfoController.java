package me.igorkudashev.crud.controller;

import lombok.RequiredArgsConstructor;
import me.igorkudashev.crud.dto.request.AddGradeInfoRequest;
import me.igorkudashev.crud.dto.request.PatchGradeInfoRequest;
import me.igorkudashev.crud.exception.DayNotFoundException;
import me.igorkudashev.crud.exception.LessonNotFoundException;
import me.igorkudashev.crud.exception.UserNotFoundException;
import me.igorkudashev.crud.model.Day;
import me.igorkudashev.crud.model.GradeInfo;
import me.igorkudashev.crud.model.Lesson;
import me.igorkudashev.crud.model.User;
import me.igorkudashev.crud.repository.DayRepository;
import me.igorkudashev.crud.repository.GradeInfoRepository;
import me.igorkudashev.crud.repository.LessonRepository;
import me.igorkudashev.crud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gradeinfo")
@RequiredArgsConstructor
public class GradeInfoController {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    private final DayRepository dayRepository;
    private final GradeInfoRepository gradeInfoRepository;

    @PostMapping("")
    public ResponseEntity<Void> addGrade(@Valid @RequestBody AddGradeInfoRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Lesson lesson = lessonRepository
                .findById(request.getLessonId())
                .orElseThrow(LessonNotFoundException::new);
        Day day = dayRepository
                .findById(request.getDateId())
                .orElseThrow(DayNotFoundException::new);

        gradeInfoRepository.save(new GradeInfo()
                .setUser(user)
                .setLesson(lesson)
                .setGrade(request.getGrade())
                .setDate(day));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patchGrade(@PathVariable("id") Long id,
                                           @RequestBody String grade) {
        if (gradeInfoRepository.findById(id).isPresent()) {
            GradeInfo gradeIndo = gradeInfoRepository.getById(id);
            gradeIndo.setGrade(grade);
            gradeInfoRepository.save(gradeIndo);
            return new ResponseEntity<>(gradeIndo.getGrade(), HttpStatus.OK);
        }
        return new ResponseEntity<>(" ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    public ResponseEntity<List<GradeInfo>> getAllTable(){
        return new ResponseEntity<>(gradeInfoRepository.findAll(), HttpStatus.OK);
    }
}
