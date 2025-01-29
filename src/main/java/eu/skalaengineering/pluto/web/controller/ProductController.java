package eu.skalaengineering.pluto.web.controller;

import eu.skalaengineering.pluto.service.ProductService;
import eu.skalaengineering.pluto.web.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static eu.skalaengineering.pluto.config.ApiConstants.CREATE_PRODUCT_V1_API_PATH;
import static eu.skalaengineering.pluto.config.ApiConstants.SECURE_API_PATH;

@RestController
@RequestMapping(SECURE_API_PATH)
@Tag(name = "Product controller")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping(CREATE_PRODUCT_V1_API_PATH)
	@Operation(description = "Saves product with provided data. If product ID is not provided, app will create one and return it")
	public ResponseEntity<UUID> createNewProduct(@RequestBody ProductDTO productDTO) {
		UUID savedProductId = productService.saveProduct(productDTO);
		return ResponseEntity.ok(savedProductId);
	}
}
