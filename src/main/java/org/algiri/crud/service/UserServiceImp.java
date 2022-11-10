package org.algiri.crud.service;

import lombok.RequiredArgsConstructor;
import org.algiri.crud.model.Day;
import org.algiri.crud.model.Group;
import org.algiri.crud.model.Lesson;
import org.algiri.crud.repository.DayRepository;
import org.algiri.crud.repository.GroupRepository;
import org.algiri.crud.repository.LessonRepository;
import org.algiri.crud.repository.UserRepository;
import org.algiri.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Transactional
@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final DayRepository dayRepository;
    private final GroupRepository groupRepository;
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
            user.setPassword(encoder.encode(user.getPassword()));
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

        groupRepository.save(new Group().setName("ИС214"));

        add(new User()
                .setName("admin")
                .setPassword("admin")
                .setStudentName("Вихров В.С")
                .addRole(roleService.findByName("ROLE_ADMIN")));
        add(new User()
                .setName("Бабкин Даниил")
                .setPassword("student")
                .setStudentName("Бабкин Даниил")
                .setGroup(groupRepository.findByName("ИС214"))
                .addRole(roleService.findByName("ROLE_USER")));
        add(new User()
                .setName("Бруданин Данилка")
                .setPassword("student")
                .setStudentName("Бруданин Данилка")
                .setGroup(groupRepository.findByName("ИС214"))
                .addRole(roleService.findByName("ROLE_USER")));


        lessonRepository.save(new Lesson().setName("ТРПО"));
        dayRepository.save(new Day().setName("31.10.2022"));
    }
}
