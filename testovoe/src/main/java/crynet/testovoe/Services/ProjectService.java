package crynet.testovoe.Services;

import crynet.testovoe.Domain.Entities.ProjectEntity;
import crynet.testovoe.Repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectEntity> getAllProjects() {
        return StreamSupport.stream(projectRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    public Optional<ProjectEntity> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    public boolean isExists(Long id) {
        return projectRepository.existsById(id);
    }

    public ProjectEntity createProject(ProjectEntity project) {
        return projectRepository.save(project);
    }

    public ProjectEntity updateProject(Long id, ProjectEntity updatedProject) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(updatedProject.getName());
                    return projectRepository.save(project);
                }).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
