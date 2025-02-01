package eu.skalaengineering.pluto.db.repository;

import eu.skalaengineering.pluto.db.entity.ProductEntity;
import eu.skalaengineering.pluto.db.entity.SoldProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProductsEntity, Long> {

	List<SoldProductsEntity> findAllByProduct(ProductEntity productEntity);
}
