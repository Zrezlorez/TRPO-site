package org.algiri.crud.service;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.model.Day;
import org.algiri.crud.model.Lesson;
import org.algiri.crud.model.User;
import org.algiri.crud.repository.DayRepository;
import org.algiri.crud.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author RulleR
 */
@Transactional
@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final DayRepository dayRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;



    @Override
    public void add(User user) {
        if (user.getId() != null) {
            Optional<User> savedUser = userRepository.findById(user.getId());
            if (savedUser.isPresent() && user.getPassword().equals(savedUser.get().getPassword())) {
                user.setPassword(encoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public boolean update(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            user.setId(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getUserByAuth(Authentication authentication) {
        return authentication == null
                ? null
                : findById(((User) authentication.getPrincipal()).getId());
    }

    @PostConstruct
    public void fillDataBase() {
        findAll().forEach(user -> deleteById(user.getId()));


        Lesson lesson1 = new Lesson().setName("инглиш").setGrade("5");
        Lesson lesson2 = new Lesson().setName("вышмат").setGrade("H");

        User user1 = new User("a", "a",
                "данил",
                roleService.findByName("ROLE_ADMIN")).setLessons(Set.of(lesson1, lesson2));
        User user2 = new User("zrezlorez", "zxc",
                "тимофей",
                roleService.findByName("ROLE_USER"));
        add(user1);
        add(user2);


        dayRepository.save(new Day().setDate("10.05.22").setUsers(Set.of(user1, user2)));
        dayRepository.save(new Day().setDate("23.11.22"));



    }
}
