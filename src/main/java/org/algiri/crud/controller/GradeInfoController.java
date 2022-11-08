package org.algiri.crud.controller;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.dto.request.AddGradeInfoRequest;
import org.algiri.crud.dto.request.FindGradeInfoRequest;
import org.algiri.crud.exception.DayNotFoundException;
import org.algiri.crud.exception.LessonNotFoundException;
import org.algiri.crud.exception.UserNotFoundException;
import org.algiri.crud.model.Day;
import org.algiri.crud.model.GradeInfo;
import org.algiri.crud.model.Lesson;
import org.algiri.crud.model.User;
import org.algiri.crud.repository.DayRepository;
import org.algiri.crud.repository.GradeInfoRepository;
import org.algiri.crud.repository.LessonRepository;
import org.algiri.crud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<GradeInfo> addGrade(@Valid @RequestBody AddGradeInfoRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);
        Lesson lesson = lessonRepository
                .findById(request.getLessonId())
                .orElseThrow(LessonNotFoundException::new);
        Day day = dayRepository
                .findById(request.getDateId())
                .orElseThrow(DayNotFoundException::new);

        GradeInfo grade = new GradeInfo()
                .setUser(user)
                .setLesson(lesson)
                .setGrade(request.getGrade())
                .setDay(day);
        gradeInfoRepository.save(grade);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @PostMapping("/find")
    public ResponseEntity<GradeInfo> findGrade(@Valid @RequestBody FindGradeInfoRequest request) {
        GradeInfo gradeInfo = gradeInfoRepository.find(request.getUserId(), request.getDayId());
        if(gradeInfo==null) {
            AddGradeInfoRequest zxc = new AddGradeInfoRequest();
            zxc.setUserId(request.getUserId());
            zxc.setLessonId(1L);
            zxc.setDateId(request.getDayId());
            zxc.setGrade(" ");
            gradeInfo = addGrade(zxc).getBody();
        }
        return new ResponseEntity<>(gradeInfo, HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchGrade(@PathVariable("id") Long id,
                                           @RequestBody String grade) {
        if (gradeInfoRepository.findById(id).isPresent()) {
            GradeInfo gradeIndo = gradeInfoRepository.getById(id);
            gradeIndo.setGrade(grade);
            gradeInfoRepository.save(gradeIndo);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    public ResponseEntity<List<GradeInfo>> getAllTable(){
        return new ResponseEntity<>(gradeInfoRepository.findAll(), HttpStatus.OK);
    }
}
