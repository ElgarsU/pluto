package skalaengineering.pluto.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import skalaengineering.pluto.enums.Currency;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
@Entity(name = "Product")
@SuperBuilder
public class ProductEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "product_id", unique = true, nullable = false)
	private UUID productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_price", nullable = false)
	@SuppressWarnings("squid:S1135") //Suppress SonarLint TODO warnings for development phase
	//TODO implement a more advanced price/currency type based on BigDecimal with rounding etc.
	private double productPrice;

	@Column(name = "price_currency", nullable = false)
	@Enumerated(EnumType.STRING)
	private Currency priceCurrency;

	@Override
	public int hashCode() {
		return 25;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductEntity other = (ProductEntity) obj;
		return id != null && id.equals(other.id);
	}
}
