package me.igorkudashev.crud.repository;

import me.igorkudashev.crud.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
    Day findByName(String name);
}
