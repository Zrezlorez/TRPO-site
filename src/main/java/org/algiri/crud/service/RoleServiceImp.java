package org.algiri.crud.service;

import org.algiri.crud.dao.RoleDao;
import org.algiri.crud.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author RulleR
 */
@Transactional
@Service(value = "roleService")
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        roleDao.deleteById(id);
    }

    @PostConstruct
    public void fillDataBase() {
        findAll().forEach(role -> deleteById(role.getId()));
        add(new Role("ROLE_ADMIN"));
        add(new Role("ROLE_USER"));
    }
}
