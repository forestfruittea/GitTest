package crynet.testovoe.Services;

import crynet.testovoe.Domain.Entities.RecordEntity;
import crynet.testovoe.Repositories.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<RecordEntity> getAllRecords() {
        return StreamSupport.stream(recordRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }
    public Optional<RecordEntity> getRecordById(Long id) {
        return recordRepository.findById(id);
    }
    public boolean isExists(Long id) {
        return recordRepository.existsById(id);
    }

    public RecordEntity createRecord(RecordEntity record) {
        return recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }
}

