package me.igorkudashev.crud.repository;

import me.igorkudashev.crud.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByName(String name);
}
