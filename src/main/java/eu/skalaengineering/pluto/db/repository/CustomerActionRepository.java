package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActionRepository extends JpaRepository<CustomerActionEntity, Long> {
}
