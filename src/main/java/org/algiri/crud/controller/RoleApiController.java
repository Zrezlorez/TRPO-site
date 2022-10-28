package org.algiri.crud.controller;

import org.algiri.crud.model.Role;
import org.algiri.crud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RulleR
 */
@RestController
@RequestMapping("/api/roles")
@Secured("ROLE_ADMIN")
public class RoleApiController {

    private final RoleService roleService;

    @Autowired
    public RoleApiController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id) {
        Role role = roleService.findById(id);
        return role == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
