package mrs.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class OrderDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String orderNo;
	@Id
	private Integer lineNo;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	private Integer amount;
	
	private BigDecimal purchasePrice;
	
}
