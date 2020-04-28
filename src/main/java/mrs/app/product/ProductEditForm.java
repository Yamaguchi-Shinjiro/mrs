package mrs.app.product;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductEditForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String productId;
	
	@NotNull
	private String productName;
	
	@NotNull
	private String brandName;
	
	@NotNull
	private BigDecimal price;
	
	@NotNull
	private Boolean inStock;

}