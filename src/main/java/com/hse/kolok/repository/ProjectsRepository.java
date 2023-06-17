package com.hse.kolok.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hse.kolok.entity.Project;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long> {

}
