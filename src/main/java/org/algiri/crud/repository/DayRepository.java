package org.algiri.crud.repository;

import org.algiri.crud.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Long> {
    Day findByName(String name);
}
