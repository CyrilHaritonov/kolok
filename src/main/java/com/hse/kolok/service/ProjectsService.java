package com.hse.kolok.service;

import com.hse.kolok.entity.Project;
import com.hse.kolok.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    public List<Project> getProjects() {
        return projectsRepository.findAll();
    }

    public void saveProject(Project project) {
        projectsRepository.save(project);
    }

    public Optional<Project> readProject(Long id) {
        return projectsRepository.findById(id);
    }

    public void deleteProject(Long id) {
        projectsRepository.deleteById(id);
    }
}
