package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	Optional<ProductEntity> findByProductId(UUID uuid);
}
