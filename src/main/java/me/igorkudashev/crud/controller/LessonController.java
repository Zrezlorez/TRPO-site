package me.igorkudashev.crud.controller;

import lombok.RequiredArgsConstructor;
import me.igorkudashev.crud.model.Lesson;
import me.igorkudashev.crud.repository.LessonRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonRepository lessonRepository;
    @PostMapping("/")
    public Long addLesson(@RequestBody String name) {
        Lesson lesson = lessonRepository
                .findByName(name);
        if(lesson!=null) throw new RuntimeException("lesson is arleady created");

        return lessonRepository.save(new Lesson().setName(name)).getId();
    }
}
