package crynet.testovoe2.Repositories;

import crynet.testovoe2.Domain.Entities.RecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends CrudRepository<RecordEntity, Long> {
}
