package eu.skalaengineering.pluto.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sold_products")
@Entity(name = "SoldProducts")
@SuperBuilder
public class SoldProductsEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "quantity")
	private Long quantity;

	@OneToOne()
	@JoinColumn(name = "product_id")
	private ProductEntity product;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "sales_data_id")
	private SalesTransactionDataEntity salesData;

	@Override
	public int hashCode() {
		return 31;
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
		SoldProductsEntity other = (SoldProductsEntity) obj;
		return id != null && id.equals(other.id);
	}
}
