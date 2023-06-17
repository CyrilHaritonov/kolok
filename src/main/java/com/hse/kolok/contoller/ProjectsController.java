package com.hse.kolok.contoller;

import com.hse.kolok.entity.Project;
import com.hse.kolok.service.ProjectsService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("")
    public ResponseEntity<List<Project>> getProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getProjects());
    }

    @PostMapping("")
    public ResponseEntity<String> createProject(@RequestBody Project project) {
        if (project.getName() == null || project.getDescription() == null
                || project.getTeammates() == null || project.getDeadline() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("One or multiple of the required fields are not present");
        }
        if (project.getName().isBlank() || project.getDescription().isBlank() || project.getTeammates().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("One or multiple of the required fields are empty");
        }
        projectsService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body("Project was successfully created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> readProject(@PathVariable("id") Long id) {
        Optional<Project> project = projectsService.readProject(id);
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(project.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@RequestBody Project project, @PathVariable("id") Long id) {
        Optional<Project> projectOptional = projectsService.readProject(id);
        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project with such id");
        }
        if (project.getName() != null) {
            projectOptional.get().setName(project.getName());
        }
        if (project.getDescription() != null) {
            projectOptional.get().setDescription(project.getDescription());
        }
        if (project.getDeadline() != null) {
            projectOptional.get().setDeadline(project.getDeadline());
        }
        if (project.getTeammates() != null) {
            projectOptional.get().setTeammates(project.getTeammates());
        }
        projectsService.saveProject(projectOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Project was successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
        Optional<Project> optionalProject = projectsService.readProject(id);
        if (optionalProject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No project with such id");
        }
        projectsService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.OK).body("Project was successfully deleted");
    }
}
