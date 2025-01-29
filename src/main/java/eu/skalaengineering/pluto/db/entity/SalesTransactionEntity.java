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
@Table(name = "sales_transaction")
@Entity(name = "SalesTransaction")
@SuperBuilder
public class SalesTransactionEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "total_amount")
	private String totalAmount;

	@OneToMany(mappedBy = "salesTransaction", cascade = CascadeType.PERSIST)
	private Set<SoldProductsEntity> soldProducts;

	public void addSoldProduct(SoldProductsEntity soldProduct) {
		if (soldProduct == null) {
			return;
		}
		soldProduct.setSalesTransaction(this);
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
		SalesTransactionEntity other = (SalesTransactionEntity) obj;
		return id != null && id.equals(other.id);
	}
}
