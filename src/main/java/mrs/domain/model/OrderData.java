package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class OrderData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	private LocalDate orderDate;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany
	@JoinColumn(name = "order_id")
	private List<OrderDetail> orderDetails;
	
}
