package org.algiri.crud.repository;

import org.algiri.crud.model.GradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeInfoRepository extends JpaRepository<GradeInfo, Long> {
    @Query("SELECT g FROM GradeInfo g WHERE g.user.id = ?1 AND g.day.id = ?2")
    GradeInfo find(Long userId, Long dayId);
}
