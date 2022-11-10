package org.algiri.crud.controller;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.model.Day;
import org.algiri.crud.model.GradeInfo;
import org.algiri.crud.repository.DayRepository;
import org.algiri.crud.repository.GradeInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/days")
@RequiredArgsConstructor
public class DayController {
    private final DayRepository dayRepository;
    private final GradeInfoRepository gradeInfoRepository;
    @PostMapping("")
    public Long addDay(@RequestBody String name) {
        Day day = dayRepository
                .findByName(name);
        if(day !=null) throw new RuntimeException("date is arleady created");
        return dayRepository.save(new Day().setName(name)).getId();
    }
    @PatchMapping("/{id}")
    public Long patchDay(@PathVariable("id") Long id,
                         @RequestBody String name) {
        Day day = dayRepository
                .getById(id);
        day.setName(name);
        return dayRepository.save(day).getId();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDay(@PathVariable("id") Long id) {
        gradeInfoRepository.deleteByDay(id);
        dayRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Day>> getAllDays(){
        return new ResponseEntity<>(dayRepository.findAll(), HttpStatus.OK);
    }
}
