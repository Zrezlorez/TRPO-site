package org.algiri.crud.dao;

import org.algiri.crud.model.Role;

import java.util.List;


public interface RoleDao {

    void add(Role role);

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    void deleteById(Long id);

}
