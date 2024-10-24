package crynet.testovoe2.Controllers;

import crynet.testovoe2.Domain.DTOs.ProjectDto;
import crynet.testovoe2.Domain.Entities.ProjectEntity;
import crynet.testovoe2.Mappers.Mapper;
import crynet.testovoe2.Services.ProjectService;
import crynet.testovoe2.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private Mapper<ProjectEntity, ProjectDto> projectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, Mapper<ProjectEntity, ProjectDto> projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return projects.stream()
                .map(projectMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        Optional<ProjectEntity> foundProject = projectService.getProjectById(id);
    return foundProject.map(projectEntity -> {
        ProjectDto projectDto = projectMapper.mapTo(projectEntity);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
        ProjectEntity projectEntity = projectMapper.mapFrom(project);
        ProjectEntity savedProjectEntity = projectService.createProject(projectEntity);
        return new ResponseEntity<>(projectMapper.mapTo(savedProjectEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id,
                                                    @RequestBody ProjectDto projectDto) {
        if (!projectService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        projectDto.setId(id);
        ProjectEntity projectEntity = projectMapper.mapFrom(projectDto);
        ProjectEntity savedProjectEntity = projectService.createProject(projectEntity);
        return new ResponseEntity<>(projectMapper.mapTo(savedProjectEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

