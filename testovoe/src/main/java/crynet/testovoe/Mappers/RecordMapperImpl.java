package crynet.testovoe.Mappers;

import crynet.testovoe.Domain.DTOs.RecordDto;
import crynet.testovoe.Domain.Entities.RecordEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RecordMapperImpl implements Mapper<RecordEntity, RecordDto> {
    public ModelMapper modelMapper;

    public RecordMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RecordDto mapTo(RecordEntity recordEntity) {
        return modelMapper.map(recordEntity, RecordDto.class);
    }
    @Override
    public RecordEntity mapFrom(RecordDto recordDto) {
        return modelMapper.map(recordDto, RecordEntity.class);
    }
}
