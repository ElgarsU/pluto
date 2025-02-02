package skalaengineering.pluto.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import skalaengineering.pluto.enums.ActionType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_action")
@Entity(name = "CustomerAction")
@SuperBuilder
public class CustomerActionEntity extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Long id;

	@Column(name = "action_type")
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@Column(name = "customer_id")
	private UUID customerId;

	@Column(name = "product_id")
	private UUID productId;

	@Column(name = "session_id")
	private UUID sessionId;

	@OneToOne()
	@JoinColumn(name = "sales_transaction_id")
	private SalesTransactionEntity salesTransaction;

	@Override
	public int hashCode() {
		return 29;
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
		CustomerActionEntity other = (CustomerActionEntity) obj;
		return id != null && id.equals(other.id);
	}
}
