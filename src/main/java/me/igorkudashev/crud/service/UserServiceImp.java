package me.igorkudashev.crud.service;

import lombok.RequiredArgsConstructor;
import me.igorkudashev.crud.repository.UserRepository;
import me.igorkudashev.crud.model.User;
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



        User user1 = new User()
                .setName("a")
                .setPassword("a")
                .setStudent("данил")
                .addRole(roleService.findByName("ROLE_ADMIN"));

        User user2 = new User()
                .setName("zrezlorez")
                .setPassword("zxc")
                .setStudent("тимофей")
                .addRole(roleService.findByName("ROLE_USER"));
        add(user1);
        add(user2);


    }
}
