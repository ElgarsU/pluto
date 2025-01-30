package eu.skalaengineering.pluto.service;

import eu.skalaengineering.pluto.db.entity.ProductEntity;
import eu.skalaengineering.pluto.db.repository.ProductRepository;
import eu.skalaengineering.pluto.web.dto.ProductDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public UUID saveProduct(ProductDTO productDTO) {

		//Implement request object field validation

		var productToSave = mapToProductEntity(productDTO);

		return productRepository.save(productToSave).getProductId();
	}

	public ProductEntity mapToProductEntity(ProductDTO productDTO) {
		//If we are provided with unique product ID we store it, if not, we create our own and return it to caller
		UUID productId = productDTO.productId() == null ? UUID.randomUUID() : productDTO.productId();

		//Sometimes I use Mapstruct for mapping needs but for MVP manual mapping will suffice
		return ProductEntity.builder()
				.productId(productId)
				.productName(productDTO.productName())
				.productPrice(productDTO.productPrice())
				.priceCurrency(productDTO.priceCurrency())
				.build();
	}

	public List<ProductDTO> getAllProducts() {
		var productEntities = productRepository.findAll();
		List<ProductDTO> products = new ArrayList<>();
		productEntities.forEach(product -> products.add(ProductDTO.builder()
				.productId(product.getProductId())
				.productName(product.getProductName())
				.productPrice(product.getProductPrice())
				.priceCurrency(product.getPriceCurrency())
				.build()));
		return products;
	}
}
