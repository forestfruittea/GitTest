package crynet.testovoe.Controllers;

import crynet.testovoe.Domain.DTOs.RecordDto;
import crynet.testovoe.Domain.Entities.RecordEntity;
import crynet.testovoe.Mappers.Mapper;
import crynet.testovoe.Services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/records")
public class RecordController {
    private final RecordService recordService;
    private Mapper<RecordEntity, RecordDto> recordMapper;

    @Autowired
    public RecordController(RecordService recordService, Mapper<RecordEntity, RecordDto> recordMapper) {
        this.recordService = recordService;
        this.recordMapper = recordMapper;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<RecordDto> getAllRecords() {
        List<RecordEntity> records = recordService.getAllRecords();
        return records.stream()
                .map(recordMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RecordDto> getRecordById(@PathVariable Long id) {
        Optional<RecordEntity> foundRecord = recordService.getRecordById(id);
        return foundRecord.map(recordEntity -> {
            RecordDto recordDto = recordMapper.mapTo(recordEntity);
            return new ResponseEntity<>(recordDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RecordDto> createRecord(@RequestBody RecordDto recordDto) {
        RecordEntity recordEntity = recordMapper.mapFrom(recordDto);
        RecordEntity savedRecordEntity = recordService.createRecord(recordEntity);
        return new ResponseEntity<>(recordMapper.mapTo(savedRecordEntity), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecordDto> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
