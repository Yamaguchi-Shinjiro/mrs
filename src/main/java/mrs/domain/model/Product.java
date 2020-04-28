package mrs.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String productId;
	
	private String productName;
	
	private String brandName;
	
	private BigDecimal price;
	
	private Boolean inStock;

}
