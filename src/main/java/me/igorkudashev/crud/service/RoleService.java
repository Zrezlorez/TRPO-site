package me.igorkudashev.crud.service;

import me.igorkudashev.crud.model.Role;

import java.util.List;

public interface RoleService {

    void add(Role role);

    Role findById(Long id);

    List<Role> findAll();

    Role findByName(String name);

    void deleteById(Long id);
}
