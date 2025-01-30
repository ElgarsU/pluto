package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.CustomerActionEntity;
import eu.skalaengineering.pluto.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerActionRepository extends JpaRepository<CustomerActionEntity, Long> {

	Optional<CustomerActionEntity> findCustomerActionByCustomerId(UUID customerId);

	@Query(value = """
			SELECT ca FROM CustomerAction ca
			WHERE ca.actionType = :actionType
			AND ca.created >= :periodStart
			AND ca.created <= :periodEnd""")
	List<CustomerActionEntity> findSalesActionsInPeriod(@Param("periodStart") LocalDateTime periodStart,
														@Param("periodEnd") LocalDateTime periodEnd,
														@Param("actionType") ActionType purchaseCompleted);

	long countByActionType(@Param("actionType") ActionType purchaseCompleted);

	//We can of course return count here also, but I opted to use derived query methods here
	List<CustomerActionEntity> findAllByActionTypeAndProductId(ActionType actionType, UUID productId);
}
