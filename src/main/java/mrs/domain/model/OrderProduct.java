package mrs.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer lineNo;
	
	private String productId;
	
	private String productName;
	
	private String brandName;
	
	private BigDecimal price;
	
	private Boolean inStock;
	
	private Integer amount;	
}
