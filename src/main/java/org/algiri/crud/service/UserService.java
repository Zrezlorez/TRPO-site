package org.algiri.crud.service;

import org.algiri.crud.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * @author RulleR
 */
public interface UserService {

    void add(User user);

    boolean update(Long id, User user);

    User findById(Long id);

    List<User> findAll();

    boolean deleteById(Long id);

    User getByName(String name);

    User getUserByAuth(Authentication authentication);

}
