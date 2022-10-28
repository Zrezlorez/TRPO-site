package org.algiri.crud.dao;

import org.algiri.crud.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * @author RulleR
 */
@Transactional
@Repository(value = "RoleDao")
public class RoleDaoImp implements RoleDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }

    @Override
    public Role findByName(String name) {
        return entityManager.createQuery("select a from Role a where a.role=:name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
