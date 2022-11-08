package org.algiri.crud.service;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.model.Role;
import org.algiri.crud.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Transactional
@Service(value = "roleService")
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByRole(name);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @PostConstruct
    public void fillDataBase() {
        findAll().forEach(role -> deleteById(role.getId()));
        add(new Role("ROLE_ADMIN"));
        add(new Role("ROLE_USER"));
    }
}
