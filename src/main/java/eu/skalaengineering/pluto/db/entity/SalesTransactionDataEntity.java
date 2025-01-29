package eu.skalaengineering.pluto.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales_transaction_data")
@Entity(name = "SalesTransactionData")
@SuperBuilder
public class SalesTransactionDataEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "total_amount")
	private String totalAmount;

	@OneToMany(mappedBy = "salesData", cascade = CascadeType.PERSIST)
	private Set<SoldProductsEntity> soldProducts;

	public void addSoldProduct(SoldProductsEntity soldProduct) {
		if (soldProduct == null) {
			return;
		}
		soldProduct.setSalesData(this);
		this.soldProducts.add(soldProduct);
	}

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
		SalesTransactionDataEntity other = (SalesTransactionDataEntity) obj;
		return id != null && id.equals(other.id);
	}
}
