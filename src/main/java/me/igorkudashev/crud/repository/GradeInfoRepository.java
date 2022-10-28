package me.igorkudashev.crud.repository;

import me.igorkudashev.crud.model.GradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeInfoRepository extends JpaRepository<GradeInfo, Long> {
}