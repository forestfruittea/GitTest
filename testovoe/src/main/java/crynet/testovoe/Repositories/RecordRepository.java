package crynet.testovoe.Repositories;

import crynet.testovoe.Domain.Entities.RecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends CrudRepository<RecordEntity, Long> {
}
