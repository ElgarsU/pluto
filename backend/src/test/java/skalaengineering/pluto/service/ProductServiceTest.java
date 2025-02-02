package skalaengineering.pluto.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skalaengineering.pluto.db.entity.ProductEntity;
import skalaengineering.pluto.db.repository.ProductRepository;
import skalaengineering.pluto.enums.Currency;
import skalaengineering.pluto.web.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Test
	void shouldSaveAndReturnProductId() {
		// Setup
		ProductDTO productDTO = ProductDTO.builder()
				.productName("Phone")
				.productPrice(500.00)
				.priceCurrency(Currency.EUR)
				.build();

		// Perform
		UUID productId = productService.saveProduct(productDTO);

		// Assert
		assertNotNull(productId);

		Optional<ProductEntity> savedProduct = productRepository.findByProductId(productId);
		assertTrue(savedProduct.isPresent());
		assertEquals("Phone", savedProduct.get().getProductName());
		assertEquals(500.00, savedProduct.get().getProductPrice());
		assertEquals(Currency.EUR, savedProduct.get().getPriceCurrency());
	}

	@Test
	void shouldGetAllProducts() {
		// Setup
		productRepository.save(ProductEntity.builder()
				.productId(UUID.randomUUID())
				.productName("Phone")
				.productPrice(500.00)
				.priceCurrency(Currency.EUR)
				.build());

		productRepository.save(ProductEntity.builder()
				.productId(UUID.randomUUID())
				.productName("Laptop")
				.productPrice(1200.00)
				.priceCurrency(Currency.EUR)
				.build());

		// Perform
		List<ProductDTO> products = productService.getAllProducts();

		// Assert
		assertEquals(5, products.size());
		assertEquals("Phone", products.get(3).productName());
		assertEquals("Laptop", products.get(4).productName());
	}

	@Test
	void shouldGetProductById() {
		// Setup
		UUID productId = UUID.randomUUID();
		productRepository.save(ProductEntity.builder()
				.productId(productId)
				.productName("Tablet")
				.productPrice(200.00)
				.priceCurrency(Currency.EUR)
				.build());

		// Perform
		Optional<ProductEntity> product = productService.getProductById(productId);

		// Assert
		assertTrue(product.isPresent());
		assertEquals("Tablet", product.get().getProductName());
		assertEquals(200.00, product.get().getProductPrice());
		assertEquals(Currency.EUR, product.get().getPriceCurrency());
	}
}
