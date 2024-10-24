package crynet.testovoe.Mappers;

import crynet.testovoe.Domain.DTOs.ProjectDto;
import crynet.testovoe.Domain.Entities.ProjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapperImpl implements Mapper<ProjectEntity, ProjectDto> {
    private ModelMapper modelMapper;

    public ProjectMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectDto mapTo(ProjectEntity projectEntity) {
        return modelMapper.map(projectEntity, ProjectDto.class);
    }

    @Override
    public ProjectEntity mapFrom(ProjectDto projectDto) {
        return modelMapper.map(projectDto, ProjectEntity.class);
    }
}
