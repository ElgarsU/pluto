package skalaengineering.pluto.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skalaengineering.pluto.db.entity.ProductEntity;
import skalaengineering.pluto.db.entity.SoldProductsEntity;

import java.util.List;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProductsEntity, Long> {

	List<SoldProductsEntity> findAllByProduct(ProductEntity productEntity);
}
