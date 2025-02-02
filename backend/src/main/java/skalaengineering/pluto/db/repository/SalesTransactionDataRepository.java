package skalaengineering.pluto.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skalaengineering.pluto.db.entity.SalesTransactionEntity;

@Repository
public interface SalesTransactionDataRepository extends JpaRepository<SalesTransactionEntity, Long> {
}
