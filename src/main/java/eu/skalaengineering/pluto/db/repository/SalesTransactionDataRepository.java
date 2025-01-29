package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.SalesTransactionDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesTransactionDataRepository extends JpaRepository<SalesTransactionDataEntity, Long> {
}
