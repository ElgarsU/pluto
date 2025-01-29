package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerActionRepository extends JpaRepository<CustomerActionEntity, Long> {

	Optional<CustomerActionEntity> findCustomerActionByCustomerId(UUID customerId);
}
