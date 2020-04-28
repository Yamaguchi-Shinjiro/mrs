package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
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
	private String orderNo;

	private LocalDateTime orderDate;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany
	@JoinColumn(name = "orderNo")
	private List<OrderDetail> orderDetails;

}
