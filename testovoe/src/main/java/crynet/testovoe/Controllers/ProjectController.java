package crynet.testovoe.Controllers;

import crynet.testovoe.Domain.DTOs.ProjectDto;
import crynet.testovoe.Domain.DTOs.RecordDto;
import crynet.testovoe.Domain.Entities.ProjectEntity;
import crynet.testovoe.Domain.Entities.RecordEntity;
import crynet.testovoe.Mappers.Mapper;
import crynet.testovoe.Mappers.ProjectMapperImpl;
import crynet.testovoe.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ProjectDto> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return projects.stream()
                .map(projectMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        Optional<ProjectEntity> foundProject = projectService.getProjectById(id);
    return foundProject.map(projectEntity -> {
        ProjectDto projectDto = projectMapper.mapTo(projectEntity);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto project) {
        ProjectEntity projectEntity = projectMapper.mapFrom(project);
        ProjectEntity savedProjectEntity = projectService.createProject(projectEntity);
        return new ResponseEntity<>(projectMapper.mapTo(savedProjectEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectDto> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

