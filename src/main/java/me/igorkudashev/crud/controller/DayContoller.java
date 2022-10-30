package me.igorkudashev.crud.controller;

import lombok.RequiredArgsConstructor;
import me.igorkudashev.crud.model.Day;
import me.igorkudashev.crud.model.GradeInfo;
import me.igorkudashev.crud.repository.DayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/days")
@RequiredArgsConstructor
public class DayContoller {
    private final DayRepository dayRepository;
    @PostMapping("")
    public Long addDay(@RequestBody String name) {
        Day Day = dayRepository
                .findByName(name);
        if(Day !=null) throw new RuntimeException("date is arleady created");

        return dayRepository.save(new Day().setName(name)).getId();
    }

    @GetMapping("")
    public ResponseEntity<List<Day>> getAllDays(){
        return new ResponseEntity<>(dayRepository.findAll(), HttpStatus.OK);
    }
}
