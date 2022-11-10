package org.algiri.crud.controller;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.exception.GroupExistsException;
import org.algiri.crud.model.Group;
import org.algiri.crud.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupRepository groupRepository;
    @PostMapping("/add")
    public Long addGroup(@RequestBody String name) {
        Group Group = groupRepository
                .findByName(name);
        if(Group !=null) throw new GroupExistsException();
        return groupRepository.save(new Group().setName(name)).getId();
    }
    @GetMapping("/{name}")
    public ResponseEntity<Group> getGroupByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(
                groupRepository.findByName(name),
                HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Group>> getAllGroups(){
        return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
    }
}
